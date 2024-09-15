# Servidor de repositório de mídias.
O repositório de mídia é um servidor que armazena vários tipos de mídias, como imagens, vídeos gifs e documentos, podendo ser utilizado como um microservice de armazamento integrando a alguma arquitetura maior, havendo a possibilidade de
realizar uploads de imagens privadas, que só podem ser baixadas com autorização.

## Recursos
- ***Armazenamento***: Armazenamento simples no Sistema Operacional.

## Salvando e Acessando as mídias
Para salvar e acessar as mídias são utilizados as requisições POST e GET rescpectivamente.

Para acessar o Repositório de Mídias Sanisamoj visite:
> www.sanisamojrepository.com/image-repo
> 
> https://www.sanisamojrepository.com/image-repo/media?media=aQgM8v1OW7lMXJOioZovqqwPC9e1w3hT2P8r-giphy2.webp

Você pode buscar um arquivo de mídia específico utilizando o seguinte endpoint:

- Pesquisar por Mídia:
> Endpoint: /media?media=media.jpeg
> 
> Exemplo: /media?media=minhaimagem.jpg
> 
> Exemplo: /media/private?media=minhaimagem.jpg&code=dsad
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
        "filename": "ePm2UwkgKVgfjOX4zr2U3iYbneVIfzgjLQMQ-wp5284283.jpg",
        "private": false,
        "code": null
    }
]
```

```json
[
  {
    "filename": "JQV5kpwNP5pKikIBacBQpxvNYc571nAFitKF-wp2741229.jpg",
    "private": true,
    "code": "ylGZhSH#Zhi8t1DFAwCywH"
  }
]
```

## Para instalação
Para instalar o projeto para testes, utilizaremos o Docker.

- Instale a última versão do **Docker** em sua máquina.
- Instale o **Mongodb** (Verifique na página oficial, ou monte uma imagem com o Docker).
- Crie um arquivo **.env** na pasta raiz do projeto, ou adicione um arquivo **.env** manualmente na construção da imagem docker.

```.env
#URL do banco de dados MONGODB
SERVER_URL=mongodb://host.docker.internal:27017
#Nome do banco de dados do MONGODB
NAME_DATABASE=ImageService

#Moderator token secret
MODERATOR_JWT_SECRET=
#Audience do token, quem deve processar o token
JWT_AUDIENCE=
#Dominio do token, quem foi o emissor
JWT_DOMAIN=

#Moderator login
MODERATOR_LOGIN=
MODERATOR_PASSWORD=
```

#### Execute o comando a seguir para construir a imagem Docker.

    docker build -t image_repo:latest .

#### Execute o comando a seguir para executar a imagem criada com o Docker.

    docker run --name image_repo -p 6868:6868 image_repo:latest

> As portas pré-definidas podem ser alteradas no arquivo *"aplication.conf"*, e devem ser refletidas na construção da imagem com o Docker.


Link para exemplos:
> https://www.sanisamojrepository.com/image-repo/media?media=tqUzlEKiwYEF8nnrVAnUsE43PRJl5vZBaGNR-sodium-odyssey.webp

> https://www.sanisamojrepository.com/image-repo/media?media=kqHjqNreBbPisZukMEQLnHapbD26P354PtT9-anime-girl-nun-with-tattoo-2k-wallpaper-uhdpaper.com-740@3@a.jpg

> https://www.sanisamojrepository.com/image-repo/media?media=ujcKswGfSeVgQzkCqlA7bWuptF3BqGOJlEyC-WhatsApp%20Image%202024-01-12%20at%2014.31.54.jpeg

> https://www.sanisamojrepository.com/image-repo/media?media=jq5Xx7GWQvJKyg0fkF1auhmcjBTGzkgB80zu-_9f9747d1-7edc-4bb8-b25a-e76dfbc812ba.jpg

> https://www.sanisamojrepository.com/image-repo/media?media=liS3bj4C7KYh4iT5B27BuOpUkDHH00FqMsF3-WhatsApp%20Image%202024-09-06%20at%2017.11.18.jpeg

> https://www.sanisamojrepository.com/image-repo/media?media=vnAX9RDO8uK3w5QxhrOimy3d2LObcDP8W3Mb-WhatsApp%20Image%202024-09-06%20at%2017.11.17.jpeg

> https://www.sanisamojrepository.com/image-repo/media?media=r7jt6m1xG0NocEBiojwrhWWcN5n74LZNnowh-giphy1.webp

> https://www.sanisamojrepository.com/image-repo/media?media=eGSgfuVwHo7EM4GIgfTCKYqWvqbHhMAGrJYb-giphy3.webp