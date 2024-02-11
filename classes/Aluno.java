package classes;

import java.util.List;

import dao.AlunoDAO;

public class Aluno {
String RA;
String nome;

	public Aluno(String RA, String nome) {
		super();
		this.RA = RA;
		this.nome = nome;
		
	}

	public String getNome() {
		return nome;
	}

	public String getRA() {
		return RA;
	}

	public void setNome(String nome) {
		this.RA = nome;
	}

	public void setRA(String RA) {
		this.RA = RA;
	}

	public boolean verificarAlunoCadastrado(String ra) {
    AlunoDAO alunoDAO = new AlunoDAO();
    return alunoDAO.verificarAlunoCadastrado(ra);
	}

	public boolean verificarDebitosAluno(String ra) {
		AlunoDAO alunoDAO = new AlunoDAO();
		return alunoDAO.verificarDebitosAluno(ra);
	}

	public boolean verificaDebito(){       
		//instancia um objeto d bito
		Debito debito = new Debito(Integer.parseInt(this.RA)); 
		/* Aqui voc  deve chamar o metodo verificaDebito da classe debito*/
		return debito.verificaDebito();
	}

	//Metodo que delega a funcionalidade de emprestar para a classe emprestimo
	public boolean Emprestar(List<Livro> livros){   
		/* Aqui voc  deve intanciar um objeto emprestimo */
		/* Aqui voc  deve chamar o metodo emprestar da classe empresitmo*/
		Emprestimo emprestimo = new Emprestimo();
		return emprestimo.emprestar(livros, this.RA);
		
	}
}
