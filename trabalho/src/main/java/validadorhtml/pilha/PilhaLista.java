package validadorhtml.pilha;

import validadorhtml.listaEncadeada.*;

public class PilhaLista<T> implements Pilha<T>{

    private ListaEncadeada<T> lista = new ListaEncadeada<>();

    @Override
    public void push(T info) {
        lista.inserir(info);
    }

    @Override
    public T pop() {
        T valor = peek();
        lista.retirar(valor);

        return valor;
    }

    @Override
    public T peek() {
        if(lista.estaVazia()){
            throw new PilhaVaziaException();
        }

        return lista.getUltimo().getInfo();
    }

    @Override
    public boolean estaVazia() {
        return lista.estaVazia();
    }

    @Override
    public void liberar(){
        try{
            while(true){
                pop();
            }
        } catch (PilhaVaziaException e){

        }
    }
    
    @Override
    public String toString(){
        String stringContent = "";
        NoLista<T> noToString = lista.getUltimo();
        while(noToString != null){
            if (noToString != lista.getUltimo()) {
                stringContent += "\n";
            }
            stringContent += noToString.getInfo();
            noToString = noToString.getProximo();
        }

        return stringContent;
    }
}