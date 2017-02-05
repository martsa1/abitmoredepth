FROM nginx:latest

COPY ./nginx.conf /etc/nginx/nginx.conf
#
# RUN apt-get update && \
#   apt get install wget && \
#   wget https://dl.eff.org/certbot-auto && \
#   chmod a+x certbot-auto && \
#   mv certbot-auto /certbot-auto
#   ./certbot-auto certonly --nginx

COPY ./jess-portfolio /usr/share/nginx/jess-portfolio
