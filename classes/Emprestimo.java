package classes;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Emprestimo {

	Date dataEmprestimo = new Date();
	Date dataPrevista = new Date();
	Date data_aux = new Date();
	String nome;
	int id;
	//Cada emprestimo tem um conjutno de itens
    List<Item> item = new ArrayList<Item>();
    int emprestimo=0;
	Aluno aluno;
	Livro livro;

	public String getRAAluno() {
		return aluno.RA;
	}

	public int getCodigoLivro(){
		return livro.codigo;
	}

	public Date getDataPrevistaDevolucao(){
		return dataPrevista;
	}

	public Date getDataEmprestimo() {
		return dataEmprestimo;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setRAAluno(String ra) {
		this.aluno.RA = ra;
	}

	public void setCodigoLivro(int codigo) {
		this.livro.codigo = codigo;
	}

	public void setDataEmprestimo(Date dataEmprestimo) {
		this.dataEmprestimo = dataEmprestimo;
	}

	public void setDataPrevistaDevolucao(Date dataPrevista) {
		this.dataPrevista = dataPrevista;
	}

	// Metodo respons vel por realizar o empr stimo
    public boolean emprestar(List<Livro> livros, String RA) {
		//TODO Auto-generated method stub
    	int aux;
    	//Para o numero de livros a ser emprestado
    	for(int i=0; i<livros.size();i++)
		//Adiciona um novo item no cojunto de items, e passa o livro a ser associado a ele
    		item.add(new Item(livros.get(i))); 
         
          //Chama o metodo para calcular a data de devolu  o caso tenha pelo menos um livro que possa ser emprestado
    		CalculaDataDevolucao();
    		System.out.print("\nNumero de livros emprestados: "+this.emprestimo);
    	    System.out.print("\nData de emprestimo: "+this.dataEmprestimo);
    	    System.out.print("\nData de devolucao: "+this.dataPrevista);
    		this.aluno.RA = RA;
			return true;
    	
	}
    
	private Date CalculaDataDevolucao()
	{   
		Date date = new Date();
		
		for(int j=0;j<item.size();j++)
		{   data_aux = item.get(j).calculaDataDevolucao(date);
		    if( dataPrevista.compareTo(data_aux) < 0)
			  dataPrevista = data_aux;
		}
		if(item.size()>2)
		{
			int tam = item.size()-2;
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(dataPrevista);
			calendar.add(Calendar.DATE, (tam*2));
	        dataPrevista = calendar.getTime();
		}
		for(int j=0;j<item.size();j++)
			item.get(j).setDataDevolucao(dataPrevista);
		
		return dataPrevista;
	
	}
}
