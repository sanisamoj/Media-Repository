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

> Endpoints disponíveis: https://documenter.getpostman.com/view/29175154/2sAXxLAZHU

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
SERVER_URL=mongodb://<user>:<password>@host.docker.internal:27017
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

#### Caso queira executar com docker-compose.

    docker-compose -p image_repo up --build -d