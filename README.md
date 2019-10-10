# Práctica Kairos Upgrade

Cómo ejecutar la aplicación:

En el zip se encuentra el código fuente (los dos proyectos con los microservicios) y el fichero docker-compose.yml. Para ejecutar la aplicación desde un terminal basta con situarnos en el mismo directorio y hacer un "docker-compose up". De esta manera se ejecutan todos los contenedores necesarios para hacer que funcione. La aplicación se levanta en el puerto 8080 del localhost.

Cómo ejecutar la aplicación en minikube:

En el zip se incluyen el deployment.yaml y el service.yaml para desplegar la aplicación en minikube. Para ello se ejecuta en una terminal los comandos kubectl apply -f service.yaml y kubectl apply -f deployment.yaml. El puerto en el que se levanta es el 30000. La ip se saca con el comando minikube ip.

Construcción de las imágenes:

  - practicaBlog: la imagen de este microservicio se construye utilizando Google Jib. Se ponen las dependencias necesarias en el POM, y se ejecuta este comando desde una terminal, situándonos en la carpeta raiz del proyecto: mvn compile com.google.cloud.tools:jib-maven-plugin:1.6.1:build -Dimage=iridior/practicablog
  
  - practicaAnalizador: utilizando el Dockerfile creado previamente, y situándonos en la carpeta raiz del proyecto, se ejecuta: docker build -t iridior/practicaanalizador .

Ejemplos:

Loguearse en la aplicación:
```
curl -k -X POST \
  http://localhost:8080/login \
  -H 'Content-Type: application/json' \
  -d '{
	"username": "admin",
	"password": "password"
}'
```

Añadir una entrada al blog:

```
curl -k -X POST \
  http://localhost:8080/blog/entradas \
  -H 'Authorization: Bearer  eyJhbGciOiJIUzUxMiJ9.eyJpYXQiOjE1NzA2MzM4MjksImlzcyI6Imh0dHBzOi8vd3d3LmF1dGVudGlhLmNvbS8iLCJzdWIiOiJhZG1pbiIsImV4cCI6MTU3MTQ5NzgyOX0.CCDmESGjuqiueBzRpckNQFVw-ZMtZ5wgO89my0m_7d8Ql1mWeYm3ILUgej-r6xqFisxLvpG_zIC8q6OCbt0y-w' \
  -H 'Content-Type: application/json' \
  -d '{
        "nombre": "nombrePrueba",
        "nick": "nickPrueba",
        "titulo": "tituloPrueba",
        "resumen": "resumenPrueba",
        "texto": "textoPrueba"
  }'
```

Añadir comentario correcto a la entrada añadida:

```
curl -k -X POST \
  http://localhost:8080/blog/comentarios/3 \
  -H 'Content-Type: application/json' \
  -d '{
        "nick": "nickPrueba",
        "contenido": "contenidoPrueba"
    }'
```

Intentar añadir comentario incorrecto a la entrada:

```
curl -k -X POST \
  http://localhost:8080/blog/comentarios/3 \
  -H 'Content-Type: application/json' \
  -d '{
        "nick": "nickPrueba",
        "contenido": "Me pica el culo"
    }'
```

Visualizar todas las entradas del blog, sin los comentarios:

```
curl -k -X GET \
  http://localhost:8080/blog/entradas \
  -H 'Content-Type: application/json'
```

Visualizar una entrada en concreto del blog, con sus comentarios:

```
curl -k -X GET \
  http://localhost:8080/blog/entradas/3 \
  -H 'Content-Type: application/json'
```