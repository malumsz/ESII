# Sistema de Biblioteca 📖
> Aplicativo em ***JAVA*** para um sistema de biblioteca na disciplina de Engenharia de Software II.

## Requisitos ⚙️

- `PostgreSQL JDBC Driver` para conexão com o banco de dados utilizando *PostgreSQL*;
- configuração da conexão com banco de dados no arquivo `ConexaoBD.java`.

## Uso 🛠️

1. Utilize o arquivo `Menu.java` para abertura do menu do aplicativo;
2. Botão `Cadastrar Livro` para cadastrar livro no banco de dados, inserindo *Título* e *ID*;
3. Botão `Cadastrar Aluno` para cadastrar aluno no banco de dados, inserindo *Nome* e *RA*;
4. Botão `Emprestar Livro` para realizar empréstimo de livro no banco de dados, inserindo *RA do aluno*, *Código do livro* e *Data prevista de devolução*;
5. Botão `Devolver Livro` para realizar devolução de livro no banco de dados, inserindo *RA do aluno*, *Código do livro* e atualizando o *status* do livro para **disponível**. Caso haja devolução atrasada por parte do aluno, cadastra um **débito** para o mesmo;
6. Botão `Buscar` onde pode-se realizar uma busca por **alunos** ou **livros** cadastrados no banco de dados, mostrando seus dados e empréstimos.

## Documentação 📄

- [PDF](PDF)

#
