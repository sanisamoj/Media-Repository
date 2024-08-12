package com.sanisamoj.data.pages

import kotlinx.html.*

fun HTML.HomePage(mediaNameList: List<String>) {
    head {
        meta(charset = "UTF-8")
        meta(name = "viewport", content = "width=device-width, initial-scale=1.0")
        title("Servidor de Repositório de Mídias")
        style {
            unsafe {
                raw("""
                        body {
                            font-family: Arial, sans-serif;
                            background-color: #f9f9f9;
                            color: #333;
                            margin: 0;
                            padding: 0;
                            display: flex;
                            justify-content: center;
                            align-items: center;
                            height: 100vh;
                        }
                        .container {
                            max-width: 800px;
                            margin: 0 auto;
                            padding: 20px;
                            background-color: #fff;
                            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                            border-radius: 8px;
                        }
                        h1, h2 {
                            color: #007BFF;
                        }
                        p, ul, li {
                            font-size: 1.1em;
                            line-height: 1.6;
                        }
                        a {
                            color: #007BFF;
                            text-decoration: none;
                        }
                        a:hover {
                            text-decoration: underline;
                        }
                        pre {
                            background-color: #f4f4f4;
                            padding: 10px;
                            border-radius: 4px;
                            overflow-x: auto;
                        }
                    """.trimIndent())
            }
        }
    }
    body {
        div("container") {
            h1 { +"Servidor de Repositório de Mídias" }

            h2 { +"Visão Geral" }
            p { +"O repositório de mídia é um servidor que armazena vários tipos de mídias, como imagens, vídeos, gifs e documentos, podendo ser utilizado como um microservice de armazenamento integrando a arquitetura." }

            h2 { +"Recursos" }
            ul {
                li { +"Armazenamento: Armazenamento simples no Sistema Operacional." }
                li { +"Integração: Ainda não implementado" }
            }

            h2 { +"Salvando e Acessando as Mídias" }
            p { +"Para salvar e acessar as mídias são utilizados as requisições POST e GET, respectivamente." }

            h2 { +"Acessar o Repositório de Mídias Sanisamoj" }
            p {
                +"Visite: "
                a(href = "http://www.sanisamojrepository.com") { +"www.sanisamojrepository.com" }
            }
            p { +"Você pode buscar um arquivo de mídia específico utilizando o seguinte endpoint:" }

            h3 { +"Pesquisar por Mídia:" }
            p { +"Endpoint: /media?media=media.jpeg" }
            p { +"Exemplo: /media?media=minhaimagem.jpg" }
            p { +"Substitua media.jpeg ou minhaimagem.jpg pelo arquivo de mídia específico que você deseja recuperar." }

            h3 { +"Salvar Mídia:" }
            p { +"Endpoint: /media" }
            p { +"Formato: MultipartData" }

            h3 { +"Exemplo de Resposta:" }
            pre {
                code {
                    +"""
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
                        """.trimIndent()
                }
            }

            h3 { +"Links para Exemplos:" }
            ul {
                mediaNameList.forEach {
                    li { a(href = "/media?media=$it") { +"/media?media=$it" } }
                }
            }
        }
    }
}
