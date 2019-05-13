public class Livro {
    private String nome;
    private String editora;

    public Livro(String titulo, String editora) {
        this.nome = titulo;
        this.editora = editora;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEditora() {
        return editora;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }
}

