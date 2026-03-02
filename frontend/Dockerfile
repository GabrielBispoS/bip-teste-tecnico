FROM node:20-alpine AS builder
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npx ng build --configuration=production --ssr false --prerender false

FROM nginx:alpine
# Copia a configuração que acabamos de criar
COPY nginx.conf /etc/nginx/conf.d/default.conf
RUN rm -rf /usr/share/nginx/html/*
COPY --from=builder /app/dist/frontend/browser/ /usr/share/nginx/html/
EXPOSE 80