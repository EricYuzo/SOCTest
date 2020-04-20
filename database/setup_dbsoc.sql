/*
 * Setup da base e usuário (para SGBD MySQL)
 *
 * Pré-requisitos (executar como root ou outro usuário administrador do banco):
 *
 * 1 - Criar a base de dados:
 *   CREATE DATABASE DBSOC;
 *
 * 2 - Criar o usuário:
 *   CREATE USER soctest@localhost IDENTIFIED BY 'soctest';
 *   GRANT ALL PRIVILEGES ON DBSOC.* TO soctest@localhost;
 *
 * 3 - Rodando o script:
 *   3.1 - Pode executar o script automaticamente:
 *     mysql --user=soctest --password --database=DBSOC < setup_dbsoc.sql
 *   3.2 - Ou pode abrir o console do mysql e, em seguida, rodar manualmente os comandos contidos nesse script:
 *     mysql --user=soctest --password --database=DBSOC
 */

DROP TABLE IF EXISTS TB_EXAME_RISCO;
DROP TABLE IF EXISTS TB_EXAME_AVALIACAO;
DROP TABLE IF EXISTS TB_EXAME;
DROP TABLE IF EXISTS TB_FINALIDADE;
DROP TABLE IF EXISTS TB_RESULTADO;
DROP TABLE IF EXISTS TB_MEDICO;
DROP TABLE IF EXISTS TB_EMPRESA_FUNCIONARIO;
DROP TABLE IF EXISTS TB_EMPRESA;
DROP TABLE IF EXISTS TB_FUNCIONARIO;
DROP TABLE IF EXISTS TB_RISCO;
DROP TABLE IF EXISTS TB_AGENTE_RISCO;
DROP TABLE IF EXISTS TB_AVALIACAO;

CREATE TABLE TB_FINALIDADE (
  ID_FINALIDADE INT NOT NULL AUTO_INCREMENT,
  NM_FINALIDADE VARCHAR(100) NOT NULL,
  PRIMARY KEY (ID_FINALIDADE)
) ENGINE=INNODB;

CREATE TABLE TB_RESULTADO (
  ID_RESULTADO INT NOT NULL AUTO_INCREMENT,
  NM_RESULTADO VARCHAR(100) NOT NULL,
  PRIMARY KEY (ID_RESULTADO)
) ENGINE=INNODB;

CREATE TABLE TB_MEDICO (
  ID_MEDICO INT NOT NULL AUTO_INCREMENT,
  NM_MEDICO VARCHAR(100) NOT NULL,
  CD_CRM VARCHAR(15) UNIQUE NOT NULL,
  SG_UF CHAR(2) NOT NULL,
  NM_TITULO VARCHAR(15) DEFAULT '',
  PRIMARY KEY (ID_MEDICO)
) ENGINE=INNODB;

CREATE TABLE TB_EMPRESA (
  ID_EMPRESA INT NOT NULL AUTO_INCREMENT,
  NM_EMPRESA VARCHAR(100) NOT NULL,
  CD_CNPJ CHAR(14) UNIQUE NOT NULL,
  PRIMARY KEY (ID_EMPRESA)
) ENGINE=INNODB;

CREATE TABLE TB_FUNCIONARIO (
  ID_FUNCIONARIO INT NOT NULL AUTO_INCREMENT,
  NM_FUNCIONARIO VARCHAR(255) NOT NULL,
  CD_CPF CHAR(11) UNIQUE NOT NULL,
  CD_RG VARCHAR(15) NOT NULL,
  CD_EMISSOR_RG VARCHAR(10) NOT NULL,
  PRIMARY KEY (ID_FUNCIONARIO)
) ENGINE=INNODB;

CREATE TABLE TB_EMPRESA_FUNCIONARIO (
  ID_EMPRESA INT NOT NULL,
  ID_FUNCIONARIO INT NOT NULL,
  NM_SETOR VARCHAR(100) NOT NULL,
  NM_CARGO VARCHAR(100) NOT NULL,
  PRIMARY KEY (ID_EMPRESA, ID_FUNCIONARIO, NM_SETOR, NM_CARGO),
  INDEX (ID_EMPRESA),
  INDEX (ID_FUNCIONARIO),
  FOREIGN KEY (ID_EMPRESA) REFERENCES TB_EMPRESA(ID_EMPRESA),
  FOREIGN KEY (ID_FUNCIONARIO) REFERENCES TB_FUNCIONARIO(ID_FUNCIONARIO)
) ENGINE=INNODB;

CREATE TABLE TB_AGENTE_RISCO (
  ID_AGENTE INT NOT NULL AUTO_INCREMENT,
  NM_AGENTE VARCHAR(100) NOT NULL,
  PRIMARY KEY (ID_AGENTE)
) ENGINE=INNODB;

CREATE TABLE TB_RISCO (
  ID_RISCO INT NOT NULL AUTO_INCREMENT,
  ID_AGENTE INT NOT NULL,
  NM_RISCO VARCHAR(100) NOT NULL,
  PRIMARY KEY (ID_RISCO),
  INDEX (ID_AGENTE),
  INDEX (ID_AGENTE, NM_RISCO),
  UNIQUE KEY ID_AGENTE_RISCO (ID_AGENTE, NM_RISCO),
  FOREIGN KEY (ID_AGENTE) REFERENCES TB_AGENTE_RISCO(ID_AGENTE)
) ENGINE=INNODB;

CREATE TABLE TB_AVALIACAO (
  ID_AVALIACAO INT NOT NULL AUTO_INCREMENT,
  NM_AVALIACAO VARCHAR(100) NOT NULL,
  PRIMARY KEY (ID_AVALIACAO)
) ENGINE=INNODB;

CREATE TABLE TB_EXAME (
  ID_EXAME INT NOT NULL AUTO_INCREMENT,
  DT_EXAME DATETIME NOT NULL,
  ID_FINALIDADE INT NOT NULL,
  ID_RESULTADO INT NOT NULL,
  ID_MEDICO INT NOT NULL,
  ID_EMPRESA INT NOT NULL,
  ID_FUNCIONARIO INT NOT NULL,
  PRIMARY KEY (ID_EXAME),
  INDEX (ID_FINALIDADE),
  INDEX (ID_RESULTADO),
  INDEX (ID_MEDICO),
  INDEX (ID_EMPRESA, ID_FUNCIONARIO),
  FOREIGN KEY (ID_FINALIDADE) REFERENCES TB_FINALIDADE(ID_FINALIDADE),
  FOREIGN KEY (ID_RESULTADO) REFERENCES TB_RESULTADO(ID_RESULTADO),
  FOREIGN KEY (ID_MEDICO) REFERENCES TB_MEDICO(ID_MEDICO),
  FOREIGN KEY (ID_EMPRESA, ID_FUNCIONARIO) REFERENCES TB_EMPRESA_FUNCIONARIO(ID_EMPRESA, ID_FUNCIONARIO)
) ENGINE=INNODB;

CREATE TABLE TB_EXAME_RISCO (
  ID_EXAME INT NOT NULL,
  ID_RISCO INT NOT NULL,
  PRIMARY KEY (ID_EXAME, ID_RISCO),
  INDEX (ID_EXAME),
  INDEX (ID_RISCO),
  FOREIGN KEY (ID_EXAME) REFERENCES TB_EXAME(ID_EXAME),
  FOREIGN KEY (ID_RISCO) REFERENCES TB_RISCO(ID_RISCO)
) ENGINE=INNODB;

CREATE TABLE TB_EXAME_AVALIACAO (
  ID_EXAME INT NOT NULL,
  ID_AVALIACAO INT NOT NULL,
  PRIMARY KEY (ID_EXAME, ID_AVALIACAO),
  INDEX (ID_EXAME),
  INDEX (ID_AVALIACAO),
  FOREIGN KEY (ID_EXAME) REFERENCES TB_EXAME(ID_EXAME),
  FOREIGN KEY (ID_AVALIACAO) REFERENCES TB_AVALIACAO(ID_AVALIACAO)
) ENGINE=INNODB;

INSERT INTO TB_FINALIDADE (NM_FINALIDADE)
VALUES
('Admissional'),
('Periódico'),
('Demissional'),
('Retorno ao trabalho'),
('Mudança de função');

INSERT INTO TB_RESULTADO (NM_RESULTADO)
VALUES
('Apto para função'),
('Apto para função com restrições'),
('Inapto para função');

INSERT INTO TB_MEDICO (CD_CRM, SG_UF, NM_TITULO, NM_MEDICO)
VALUES
('105231', 'SP', 'Dr.',  'Eric Slywitch'),
('117154', 'SP', 'Dra.', 'Karla Daniela Santone'),
('130253', 'SP', 'Dr.',  'Alberto Peribanez Gonzalez');
  
INSERT INTO TB_EMPRESA (CD_CNPJ, NM_EMPRESA)
VALUES
('04024684000112', 'O.N.G. VIVA BICHO'),
('06136357000188', 'SOS ANIMAIS DE RUA'),
('01943493000166', 'ASSOCIACAO MUCKY DE PROTECAO AOS PRIMATAS'),
('04087616000100', 'ASERG ASSOCIACAO SANTUARIO ECOLOGICO RANCHO DOS GNOMOS');

INSERT INTO TB_FUNCIONARIO (CD_CPF, CD_RG, CD_EMISSOR_RG, NM_FUNCIONARIO)
VALUES
('20153837332', '723384535', 'SSP/SP', 'Alex'),
('03350398054', '838588926', 'SSP/SP', 'Marty'),
('33299880685', '743113089', 'SSP/SP', 'Melman'),
('77320912976', '596365785', 'SSP/SP', 'Glória'),
('89637804341', '519577116', 'SSP/SP', 'Julien'),
('99201238194', '815027583', 'SSP/SP', 'Maurice'),
('07533095595', '404608459', 'SSP/SP', 'Mort'),
('50802133202', '957537935', 'SSP/SP', 'Capitão'),
('28333694778', '634705912', 'SSP/SP', 'Kowalski'),
('45785642022', '423016991', 'SSP/SP', 'Rico'),
('34192588047', '268073030', 'SSP/SP', 'Recruta'),
('57038614094', '153924719', 'SSP/SP', 'Nana');

INSERT INTO TB_EMPRESA_FUNCIONARIO (ID_EMPRESA, ID_FUNCIONARIO, NM_SETOR, NM_CARGO)
VALUES
(1,  8, 'Pinguin', 'Chefe'),
(1,  9, 'Pinguin', 'Capacho'),
(1, 10, 'Pinguin', 'Capacho'),
(1, 11, 'Pinguin', 'Faz tudo'),
(2,  7, 'Selva',   'Faz tudo'),
(2, 12, 'Cidade',  'Chato'),
(3,  5, 'Selva',   'Rei'),
(3,  6, 'Selva',   'Escravo'),
(3,  7, 'Selva',   'Faz tudo'),
(4,  1, 'Zoo',     'Artista'),
(4,  2, 'Zoo',     'Artista'),
(4,  3, 'Zoo',     'Artista'),
(4,  4, 'Zoo',     'Artista');

INSERT INTO TB_AGENTE_RISCO (NM_AGENTE)
VALUES
('Físicos'),
('Químicos'),
('Biológicos'),
('Ergonômicos'),
('Acidentes');

INSERT INTO TB_RISCO (ID_AGENTE, NM_RISCO)
VALUES
(1, 'Ruído'),
(1, 'Vibrações'),
(1, 'Rad. ionizantes'),
(1, 'Rad. não ionizantes'),
(1, 'Frio'),
(1, 'Pressão'),
(1, 'Umidade'),
(2, 'Poeiras'),
(2, 'Óleo e Graxas'),
(2, 'Névoas'),
(2, 'Neblinas'),
(2, 'Gases'),
(2, 'Vapores'),
(3, 'Fungos'),
(3, 'Vírus'),
(3, 'Protozoários'),
(3, 'Parasitas'),
(3, 'Infecciosos'),
(4, 'Cansaço visual'),
(4, 'Ilumunamento inadequado'),
(4, 'Levantamento e transporte manual de peso'),
(4, 'Repetitividade'),
(4, 'Postura sentada por longos períodos'),
(5, 'Armazenamento inadequado'),
(5, 'Arranjo físico'),
(5, 'Atigido por'),
(5, 'Ferramentas inadequadas ou defeituosas'),
(5, 'Prensagem'),
(5, 'Queda de materiais'),
(5, 'Queda de mesmo nível'),
(5, 'Queda de níveis diferentes'),
(5, 'Atropelamento'),
(5, 'Batidas'),
(5, 'Objetos cortantes e/ou perfurantes');

INSERT INTO TB_AVALIACAO (NM_AVALIACAO)
VALUES
('Anamnese'),
('Pressão arterial e batimentos cardíacos'),
('Saúde das articulações'),
('Audiometria'),
('Acuidade Visual'),
('Espirometria'),
('EEG'),
('ECG'),
('Raio-x');

INSERT INTO TB_EXAME (DT_EXAME, ID_FINALIDADE, ID_RESULTADO, ID_MEDICO, ID_EMPRESA, ID_FUNCIONARIO)
VALUES
('2020-04-20 09:10:05.000', 2, 1, 1, 1, 9),
('2020-04-20 11:00:00.000', 1, 1, 2, 3, 7);
