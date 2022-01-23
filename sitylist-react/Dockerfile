FROM node:alpine
WORKDIR /app
COPY package.json ./
COPY package-lock.json ./
COPY ./ ./
ARG URL_CITY_API
ENV REACT_APP_URL_CITY_API $URL_CITY_API
RUN echo "DEBUG": $REACT_APP_URL_CITY_API
RUN npm i
CMD ["npm", "run", "start"]