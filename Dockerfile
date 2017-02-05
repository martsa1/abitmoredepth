FROM nginx:latest

RUN mkdir /usr/share/nginx/jess/
COPY ./index.html /usr/share/nginx/jess/index.html
COPY ./nginx.conf /etc/nginx/nginx.conf
