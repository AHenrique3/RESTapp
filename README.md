# RESTapp

Aplicação REST para o Processo Seletivo Casa e Café 2018. Desenvolvido em JAVA com suporte JSON pela biblioteca GSON

## Como Utilizar
1. Clone ou baixe o repositório.
1. Execute o arquivo RESTapp.jar no diretório RESTapp/dist/ pelo comando no terminal:
    * java -jar "RESTapp.jar" 

## Funcionamento (Última Versão)
* O servidor é iniciado na porta 8000, e pode ser acessado por [localhost:8000](localhost:8000).
* A chamada GET pode ser feita por [localhost:8000/plan](localhost:8000/plan) e a POST por [localhost:8000/payments](localhost:8000/payments).
* Outras chamadas para essas rotas retornarão o erro 403.
* Trocas de mensagens:
  * GET: O retorno é feito no formato JSON;
  * POST: O retorno é feito em texto puro; A requisição é no formato JSON;
