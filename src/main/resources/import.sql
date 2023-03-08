insert into cozinha (id, nome) values (1 ,'Tailandesa');
insert into cozinha (id, nome) values (2 ,'Indiana');

insert into estado (nome) values ('CE');
insert into estado (nome) values ('SP');
insert into estado (nome) values ('RJ');

insert into cidade (nome,estado_id) values ('Fortaleza', 1);
insert into cidade (nome,estado_id) values ('São Paulo', 2);
insert into cidade (nome,estado_id) values ('Rio de Janeiro', 3);
insert into cidade (nome,estado_id) values ('Maracanau', 1);
insert into cidade (nome,estado_id) values ('Caucaia', 1);

insert into restaurante (nome, taxa_frete,cozinha_id, data_cadastro, data_atualizacao, endereco_cidade_id, endereco_cep, endereco_complemento, endereco_logradouro, endereco_numero, endereco_bairro) values ('Comida Chinesa',100.00, 1, utc_timestamp, utc_timestamp, 1, '61000-100','Prox ao Delegacia', 'Rua Francisco Chagas', '2000', 'Industrial');
insert into restaurante (nome, taxa_frete,cozinha_id, data_cadastro, data_atualizacao, endereco_cidade_id, endereco_cep, endereco_complemento, endereco_logradouro, endereco_numero, endereco_bairro) values ('Comiada Brasileira',80.00, 1, utc_timestamp, utc_timestamp, 3, '61000-160','Prox ao Colegio', 'Rua Marta Sousa', '1542', 'Jardim Badeirantes');
insert into restaurante (nome, taxa_frete,cozinha_id, data_cadastro, data_atualizacao, endereco_cidade_id, endereco_cep, endereco_complemento, endereco_logradouro, endereco_numero, endereco_bairro) values ('Comida Francesa',90.00, 2, utc_timestamp, utc_timestamp, 2, '61930-100','Prox ao Posto de Saude', 'Rua Pedro Sampaio', '1550', 'Pajucara');
insert into restaurante (nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, endereco_cidade_id, endereco_cep, endereco_complemento, endereco_logradouro, endereco_numero, endereco_bairro) values ('Thai Gourmet', 10, 1, utc_timestamp, utc_timestamp, 1, '619321-160','Prox ao Deposito', 'Rua João Pinheiro', '1000', 'Centro');

insert into forma_pagamento (descricao) values ('Dinheiro');
insert into forma_pagamento (descricao) values ('Cartao');
insert into forma_pagamento (descricao) values ('Pix');

insert into permissao (descricao,nome) values ('Administrador','Antonio');
insert into permissao (descricao,nome) values ('Usuario','Francisco');


insert into restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) values (1, 1), (1, 2), (1, 3), (2, 3), (3, 2), (3, 3);

insert into produto (ativo, descricao, nome, preco, restaurante_id ) values (1, 'Prato de Macarão ao molho', 'Maracarão', 17, 1);
insert into produto (ativo, descricao, nome, preco, restaurante_id ) values (1, 'Arroz ao Leite', 'Arroz', 12.50, 2);
insert into produto (ativo, descricao, nome, preco, restaurante_id ) values (1, 'Feijoada', 'Feijão', 25.50, 3);
insert into produto (ativo, descricao, nome, preco, restaurante_id ) values (1, 'Baião ao Leite', 'Baião', 22, 4);
 
 
 