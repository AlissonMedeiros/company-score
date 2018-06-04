# company-score

## OpenClover

Para executar os testes unitários e obter uma análise utilizando o projeto [OpenClover](http://openclover.org/), siga os passos abaixo:

```sh
mvn clean verify -Pclover && mvn clover:aggregate clover:clover -Pclover
```

O relatório será gerado no diretório `target/.clover/report/`