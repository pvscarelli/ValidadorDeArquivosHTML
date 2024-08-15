package validadorhtml.listaEncadeada;

public class ListaEncadeada<T> {
    private NoLista<T> primeiro;
    private NoLista<T> ultimo;

    public ListaEncadeada(){
        this.primeiro = null;
        this.ultimo = null;
    }

    public NoLista<T> getPrimeiro(){
        return this.primeiro;
    }

    public void inserirNoFinal(T info){
        NoLista<T> novoNo = new NoLista<T>();    
        novoNo.setInfo(info);
        if(primeiro == null){
            primeiro = novoNo; 
        } else {
            ultimo.setProximo(novoNo);   
        }

        ultimo = novoNo;
    }

    public void inserir(T info){
        NoLista<T> novoNo = new NoLista<T>();    
        novoNo.setInfo(info);
        if(ultimo == null){
            primeiro = novoNo;
        } else {
            ultimo.setProximo(novoNo);
        }

        ultimo = novoNo;
    }

    public void retirarTodos(T valor) {
        while (primeiro != null && primeiro.getInfo().equals(valor)) {
            primeiro = primeiro.getProximo();
        }
        
        NoLista<T> atual = primeiro;
        while (atual != null && atual.getProximo() != null) {
            if (atual.getProximo().getInfo().equals(valor)) {
                atual.setProximo(atual.getProximo().getProximo());
            } else {
                atual = atual.getProximo();
            }
        }
    }
    
    public boolean estaVazia(){
        return primeiro == null;
    }

    public NoLista<T> buscar(T info){
        NoLista<T> p = primeiro;
        while(p != null){
            if(p.getInfo().equals(info)){
                return p;
            }
            p = p.getProximo();
        } 

        return null;
    }

    public void retirar(T info){
        NoLista<T> anterior = null;
        NoLista<T> u = primeiro; //ultimo? 
        
        while(u != null && !u.getInfo().equals(info)){
            anterior = u;
            u = u.getProximo();
        } 

        if(u != null){
            if(u == primeiro){
                primeiro = primeiro.getProximo();
            } else if(u == ultimo) {
                anterior.setProximo(null);
                ultimo = anterior;
            } else {
                anterior.setProximo(u.getProximo());
            }
        }
    }   
    
    public int obterComprimento(){
        int qtdeNos = 0;
        NoLista <T> noParaContagem = primeiro;
        while(noParaContagem != null){
            qtdeNos++;
            noParaContagem = noParaContagem.getProximo();
        }
        return qtdeNos;
    }

    public NoLista<T> obterNo(int index){
        if(index < 0){
            throw new java.lang.IndexOutOfBoundsException();
        }
        
        NoLista<T> noProcurado = getPrimeiro();
        while(noProcurado != null && index > 0){
            index--;
            noProcurado = noProcurado.getProximo();
        }

        if(noProcurado == null){
            throw new IndexOutOfBoundsException();
        }
        return noProcurado;        
    }

    @Override
    public String toString(){
        String stringContent = "";
        NoLista<T> noToString = primeiro;
        while(noToString != null){
            if (noToString != primeiro) {
                stringContent += ",";
            }
            stringContent += noToString.getInfo();
            noToString = noToString.getProximo();
        }
        return stringContent;
    }

    public void setPrimeiro(NoLista<T> primeiro) {
        this.primeiro = primeiro;
    }

    public NoLista<T> getUltimo() {
        return ultimo;
    }
}