package classes;

public class Livro {
	int codigo;
	String t;
	boolean exemplarBiblioteca;
	Titulo titulo;

	public Livro(int codigo, String t) {
		super();
		//inst ncia um titulo e o associa ao livro
		titulo = new Titulo(codigo, t);
		//codigo aleat rio para definir se o livro   exemplar unico
		if (codigo == 2 || codigo == 4 )
			exemplarBiblioteca =true;
		else
			exemplarBiblioteca =false;
	}

	public int verPrazo() {
		return titulo.getPrazo();
	}
	
	public boolean verificaLivro()
	{  return exemplarBiblioteca;
	   
	}
	
}