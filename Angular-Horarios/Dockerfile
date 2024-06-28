# Usar una imagen base de Node.js 18 para compilar la aplicación Angular
FROM node:18 AS build

# Establecer el directorio de trabajo
WORKDIR /app

# Copiar los archivos package.json y package-lock.json al contenedor
COPY package*.json ./

# Instalar las dependencias
RUN npm install

# Copiar el resto de los archivos del proyecto al contenedor
COPY . .

# Compilar la aplicación Angular utilizando la configuración de producción
RUN npm run build -- --configuration production

# Usar una imagen base de Nginx para servir la aplicación
FROM nginx:alpine

# Copiar los archivos compilados desde el contenedor de compilación al contenedor de Nginx
COPY --from=build /app/dist/angular-horarios/browser /usr/share/nginx/html

# Copiar el archivo de configuración de nginx
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Exponer el puerto 80 para el contenedor de Nginx
EXPOSE 80

# Comando para iniciar Nginx
CMD ["nginx", "-g", "daemon off;"]
