# Servidor de repositório de mídias.
O repositório de mídia é um servidor que armazena vários tipos de mídias, como imagens, vídeos gifs e documentos, podendo ser utilizado como um microservice de armazamento integrando a alguma arquitetura maior, havendo a possibilidade de
realizar uploads de imagens privadas, que só podem ser baixadas com autorização.

## Recursos
- ***Armazenamento***: Armazenamento simples no Sistema Operacional.

## Salvando e Acessando as mídias
Para salvar e acessar as mídias são utilizados as requisições POST e GET rescpectivamente.

Para acessar o Repositório de Mídias Sanisamoj visite:
> www.sanisamojrepository.com/image-repo

Você pode buscar um arquivo de mídia específico utilizando o seguinte endpoint:

- Pesquisar por Mídia:
> Endpoint: /media?media=media.jpeg
> 
> Exemplo: /media?media=minhaimagem.jpg
> 
> Substitua media.jpeg ou minhaimagem.jpg pelo arquivo de mídia específico que você deseja recuperar.

Exemplo de resposta:
```json
[
    {
        "filename": "ECA0TVHq2A0lQUOywHpdQWtTE4Y46obShNw5-6f8fc8fba7e5b825373c21053bed9a36.jpg",
        "private": false,
        "code": null
    },
    {
        "filename": "JQV5kpwNP5pKikIBacBQpxvNYc571nAFitKF-wp2741229.jpg",
        "private": false,
        "code": null
    },
    {
        "filename": "ePm2UwkgKVgfjOX4zr2U3iYbneVIfzgjLQMQ-wp5284283.jpg",
        "private": false,
        "code": null
    }
]
```

#### Execute o comando a seguir para construir a imagem Docker.

    docker build -t image_repo:latest .

#### Execute o comando a seguir para executar a imagem criada com o Docker.

    docker run --name image_repo -p 6868:6868 image_repo:latest

> As portas pré-definidas podem ser alteradas no arquivo *"aplication.conf"*, e devem ser refletidas na construção da imagem com o Docker.


Link para exemplos:
> [www.sanisamojrepository.com/media?media=TGK3ZYxmISMkCjxWGREq8OxLCo2Ze29dumFt-wp2741229.jpg](https://www.sanisamojrepository.com/media?media=KFjxxgxvqlVHwAOfKoFBhlxe2UHKA7CddrBe-WhatsApp%20Image%202024-07-03%20at%2019.04.52.jpeg)

> https://www.sanisamojrepository.com/media?media=Y70gAn1OleIQdKQXrEYhliuthbrqmnmZQkly-Captura%20de%20tela%202024-07-20%20230947.png
