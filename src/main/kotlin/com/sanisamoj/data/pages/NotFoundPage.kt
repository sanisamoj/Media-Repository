package com.sanisamoj.data.pages

import kotlinx.html.*

fun HTML.NotFoundPage() {
    head {
        meta(charset = "UTF-8")
        meta(name = "viewport", content = "width=device-width, initial-scale=1.0")
        title("Página Não Encontrada")
        style {
            unsafe {
                raw("""
                        body {
                            font-family: Arial, sans-serif;
                            background-color: #f4f4f4;
                            color: #333;
                            display: flex;
                            justify-content: center;
                            align-items: center;
                            height: 100vh;
                            margin: 0;
                        }
                        .container {
                            text-align: center;
                        }
                        h1 {
                            font-size: 4em;
                            margin-bottom: 0.5em;
                        }
                        p {
                            font-size: 1.5em;
                        }
                        a {
                            text-decoration: none;
                            color: #007BFF;
                        }
                        a:hover {
                            text-decoration: underline;
                        }
                    """.trimIndent())
            }
        }
    }
    body {
        div("container") {
            h1 { +"404" }
            p { +"Mídia Não Encontrada" }
            p {
                a(href = "/") { +"Voltar para a página inicial" }
            }
        }
    }
}
