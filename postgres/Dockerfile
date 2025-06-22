FROM postgres:latest
ENV POSTGRES_DB=userprofile
ENV POSTGRES_USER=postgres
ENV POSTGRES_PASSWORD=nguyenkhang
COPY init.sql /docker-entrypoint-initdb.d/
EXPOSE 5432