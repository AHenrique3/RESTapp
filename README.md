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
  * POST: O retorno é feito em texto puro; A requisição é no formato JSON; Os campos da requisição são :
    * payment_date (formato dd/mm/YYYY)
    * payment_type (string)
    * product (string)
    * product_price (int)
    * discount (string)
  * Os campos price e transaction_id são gerados internamente pelo programa
## Exemplo de Uso e retorno:
* GET localhost:8000/plans
  * Retorno:
```JSON
[
  {"product":"gold_plan","price":59.9,"description":"plano pago gold"},
  {"product":"platinum_plan","price":79.9,"description":"premium platinum"},
  {"product":"super_premium_plan","price":129.9,"description":"o melhor plano de todos"}
]
```
* POST localhost:8000/payment
  * Conteúdo:
```JSON
{
  "payment_date": "22/12/2017",
  "payment_type": "dinheiro",
  "product" : "gold_plan",
  "product_price": 59.9,
  "discount": "0.10"
}
```
  * Retorno:
  ```
  Pagamento registrado com sucesso!
  ```
