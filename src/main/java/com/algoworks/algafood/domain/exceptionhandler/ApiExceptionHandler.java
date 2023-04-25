package com.algoworks.algafood.domain.exceptionhandler;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.algoworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algoworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algoworks.algafood.domain.exception.NegocioException;
import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;	
	
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private MessageSource messageSource;
	
	//USANDO O PRADRAO RFC 7807 de Resposta de exception ao Usuario.
	
		@ExceptionHandler(EntidadeNaoEncontradaException.class)
		public ResponseEntity<?> handleEntidadeNaoEncontradaException(EntidadeNaoEncontradaException ex, WebRequest request) {
			
			//Aqui ja foi mais padronizado, o envio dos dados ao metodo. 
			HttpStatus status = HttpStatus.NOT_FOUND;
			
			ProblemType problemType = ProblemType.RECURSO_NAO_ENCONTRADO;
			
			String detail = ex.getMessage();
			
			//createProblemBuilder = é o metodo que ajuda para retorna codigo padrao. 
			Problem problem = createProblemBuilder(status, problemType, detail).build();
			
			//Chamando o metodo que passa todas a exception por ele.
			return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
		}
		
		@ExceptionHandler(EntidadeEmUsoException.class)
		public ResponseEntity<?> handleEntidadeEmUsoException(EntidadeEmUsoException ex, WebRequest request) {
			
			HttpStatus status = HttpStatus.CONFLICT;
			
			ProblemType problemType = ProblemType.ENTIDADE_EM_USO;
			
			String detail = ex.getMessage();
			
			Problem problem = createProblemBuilder(status, problemType, detail).build();
			
			//Chamando o metodo que passa todas a exception por ele.
			return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
		}
		
		@ExceptionHandler(NegocioException.class) //Classe NegocioException
		public ResponseEntity<?> handleNegocioException(NegocioException ex, WebRequest request) {
			
			//Aqui ja foi mais padronizado, o envio dos dados ao metodo. 
			HttpStatus status = HttpStatus.BAD_REQUEST;
			
			ProblemType problemType = ProblemType.ERRO_NEGOCIO;
			
			String detail = ex.getMessage();
			
			Problem problem = createProblemBuilder(status, problemType, detail).build();
			
			//Chamando o metodo que passa todas a exception por ele.
			return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
		}

	/////////////////////////// -------------TRATANDO -----------------  ////////////////////////////////////////////////////	

		//Trata erro de formatação passado para API. EX codigo:2, . a pessoa digita uma , no final.
		@Override //Sobrecrevendo Metodo padrao do spring.
		protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
				HttpHeaders headers, HttpStatusCode status, WebRequest request) {

			    Throwable rootCause = ExceptionUtils.getRootCause(ex);
				
				if (rootCause instanceof InvalidFormatException) {
					return handleInvalidFormatException((InvalidFormatException) rootCause, headers, (HttpStatus) status, request);
				
				}else if (rootCause instanceof PropertyBindingException) {
			        return handlePropertyBindingException((PropertyBindingException) rootCause, headers, (HttpStatus) status, request); 
			    }
				
				ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
				String detail = "O corpo da requisição está inválido. Verifique erro de sintaxe.";
				
				Problem problem = createProblemBuilder((HttpStatus) status, problemType, detail).build();
				
				return handleExceptionInternal(ex, problem, headers, status, request);
		}
		
		//Esse metodo é usado mais a cima.
		private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex,
				HttpHeaders headers, HttpStatus status, WebRequest request) {

			String path = joinPath(ex.getPath());
			
			ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
			String detail = String.format("A propriedade '%s' recebeu o valor '%s', "
					+ "que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s.",
					path, ex.getValue(), ex.getTargetType().getSimpleName());
			
			Problem problem = createProblemBuilder(status, problemType, detail).build();
			
			return handleExceptionInternal(ex, problem, headers, status, request);
		}	
		
		@SuppressWarnings("unused")
		private ResponseEntity<Object> handlePropertyBindingException(PropertyBindingException ex,
		        HttpHeaders headers, HttpStatus status, WebRequest request) {

		    // Criei o método joinPath para reaproveitar em todos os métodos que precisam
		    // concatenar os nomes das propriedades (separando por ".")
		    String path = joinPath(ex.getPath());
		    
		    ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
		    String detail = String.format("A propriedade '%s' não existe. "
		            + "Corrija ou remova essa propriedade e tente novamente.", path);

		    Problem problem = createProblemBuilder(status, problemType, detail).build();
		    
		    return handleExceptionInternal(ex, problem, headers, status, request);
		}
		
		private String joinPath(List<Reference> list) {
		    return list.stream()
		        .map(ref -> ref.getFieldName())
		        .collect(Collectors.joining("."));
		}
		
		//CASO Passe UMA URL que não exista.
		@Override
		protected ResponseEntity<Object> handleTypeMismatch(org.springframework.beans.TypeMismatchException ex,
				HttpHeaders headers, HttpStatusCode status, WebRequest request) {
			
			if (ex instanceof MethodArgumentTypeMismatchException) {
			        return handleMethodArgumentTypeMismatch(
			                (MethodArgumentTypeMismatchException) ex, headers, (HttpStatus) status, request);
			    }

			    return super.handleTypeMismatch(ex, headers, status, request);
		}
		//Faz parte do metodo de cima.
		private ResponseEntity<Object> handleMethodArgumentTypeMismatch(
		        MethodArgumentTypeMismatchException ex, HttpHeaders headers,
		        HttpStatus status, WebRequest request) {

		    ProblemType problemType = ProblemType.PARAMETRO_INVALIDO;

		    String detail = String.format("O parâmetro de URL '%s' recebeu o valor '%s', "
		            + "que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s.",
		            ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());

		    Problem problem = createProblemBuilder(status, problemType, detail).build();

		    return handleExceptionInternal(ex, problem, headers, status, request);
		}	
		
		//Tratando erro de formação no URL da api. EX http://localhost:8080/cidadessss/1
		@Override
		protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
				HttpStatusCode status, WebRequest request) {
				
				ProblemType problemType = ProblemType.RECURSO_NAO_ENCONTRADO;
			    String detail = String.format("O recurso %s, que você tentou acessar, é inexistente.", 
			            ex.getRequestURL());
			    
			    Problem problem = createProblemBuilder((HttpStatus) status, problemType, detail).build();
			    
			    return handleExceptionInternal(ex, problem, headers, status, request);
		}
		
	/////////////////////////// -------------FIM -----------------  ////////////////////////////////////////////////////	

		@Override
		protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
				HttpHeaders headers, HttpStatusCode status, WebRequest request) {
			
		    ProblemType problemType = ProblemType.DADOS_INVALIDOS;
		    String detail = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.";
		    
		    
		    BindingResult bindingResult = ex.getBindingResult();
		    
		    List<Problem.Field> problemFields = bindingResult.getFieldErrors().stream()
		    		.map(fieldError ->{ 
		    			
		    				String message = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
		    			
		    			     return Problem.Field.builder()
		    				.name(fieldError.getField())
		    				.userMessage(message)
		    				.build();
		    				
		    		})
		    		.collect(Collectors.toList());
		    
		    Problem problem = createProblemBuilder((HttpStatus) status, problemType, detail)
		        .userMessage(detail) 
		        .fields(problemFields)
		        .build();	
		    		
		   return handleExceptionInternal(ex, problem, headers, status, request);
		   
		}
		
		//Trata todas as Exception que não forma Mapeadas
		@ExceptionHandler(Exception.class)
		public ResponseEntity<Object> handleUncaught(Exception ex, WebRequest request) {
			
		    HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;	
		    
		    ProblemType problemType = ProblemType.ERRO_DE_SISTEMA;
		    
		    String detail = "Ocorreu um erro interno inesperado no sistema. "
		            + "Tente novamente e se o problema persistir, entre em contato "
		            + "com o administrador do sistema.";

		    // Importante colocar o printStackTrace (pelo menos por enquanto, que não estamos
		    // fazendo logging) para mostrar a stacktrace no console
		    // Se não fizer isso, você não vai ver a stacktrace de exceptions que seriam importantes
		    // para você durante, especialmente na fase de desenvolvimento
		    ex.printStackTrace();
		    
		    Problem problem = createProblemBuilder(status, problemType, detail).build();

		    return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
		}  
		
		
		//@Override Sobrecarga do Metodo da classe ResponseEntityExceptionHandler  
		protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
				HttpStatus status, WebRequest request) {
			
			if (body == null) {
				body = Problem.builder()
					.title(status.getReasonPhrase())
					.status(status.value())
					.build();
			} else if (body instanceof String) {
				body = Problem.builder()
					.title((String) body)
					.status(status.value())
					.build();
			}
			
			return super.handleExceptionInternal(ex, body, headers, status, request);
		}
		
		//Metodo auxiliar para não ficar direito criando instancia e se repetindo o codigo. 
		private Problem.ProblemBuilder createProblemBuilder(HttpStatus status,
				ProblemType problemType, String detail) {
			
			return Problem.builder()
				.status(status.value())
				.type(problemType.getUri())
				.title(problemType.getTitle())
				.detail(detail);
		}	
	
}
