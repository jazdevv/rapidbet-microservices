# Dockerfile
FROM php:8.2-cli

RUN apt-get update -y && apt-get install -y libmcrypt-dev && apt-get install -y libonig-dev && docker-php-ext-install bcmath

RUN curl -sS https://getcomposer.org/installer | php -- --install-dir=/usr/local/bin --filename=composer
RUN docker-php-ext-install pdo mbstring

WORKDIR /app
COPY . /app

RUN composer install --ignore-platform-req=ext-bcmath

EXPOSE 8000
CMD php artisan migrate:fresh && php artisan serve --host=0.0.0.0 --port=8000
