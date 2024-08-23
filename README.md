# HTML Validator

Projeto desenvolvido como trabalho final da disciplina de Estrutura de Dados, com o objetivo de validar arquivos HTML. O programa verifica se o arquivo HTML possui todas as tags necessárias, se nenhuma tag está fora de ordem, e se não há erros no arquivo. A validação é feita sem o uso de classes nativas de estrutura de dados do Java, utilizando, em vez disso, uma lista encadeada e uma pilha implementadas do zero.

## Funcionalidades

- **Validação de tags HTML:** Verifica se o arquivo HTML está correto, com todas as tags abertas e fechadas corretamente.
- **Contagem de tags:** Conta as tags presentes no arquivo HTML caso ele seja válido.
- **Interface gráfica:** Permite ao usuário selecionar o arquivo HTML que deseja validar através de uma interface gráfica amigável.

## Requisitos

- **Java 17:** O projeto foi desenvolvido utilizando Java 17. Não há garantias de funcionamento em versões anteriores.

## Como Executar

### Clonar o Repositório

```bash
git clone https://github.com/pvscarelli/ValidadorDeArquivosHTML.git
cd ValidadorDeArquivosHTML
```

## Executar o Programa

No terminal, execute o seguinte comando:

```bash
java -jar validator.jar
```

Isso vai abrir a interface gráfica do validador.

## Selecionar o Arquivo HTML

### Linux: Para obter o caminho completo do arquivo, navegue até a pasta onde o arquivo está localizado e rode o comando pwd no terminal. Em seguida, adicione o nome do arquivo ao caminho mostrado.

### Windows: Navegue até a pasta onde o arquivo está localizado. Segure a tecla Shift, clique com o botão direito do mouse no arquivo e selecione "Copiar como caminho". Cole o caminho na interface gráfica.

## Realizar a Validação

Após inserir o caminho completo do arquivo, clique em "Validar". O programa realizará a validação do arquivo e mostrará o resultado na interface gráfica.

## Estrutura de Dados

Este projeto foi desenvolvido sem o uso de classes nativas de estrutura de dados do Java, implementando do zero uma lista encadeada e uma pilha para realizar a validação das tags HTML.
