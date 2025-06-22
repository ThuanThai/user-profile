# Build stage
FROM node:20-alpine as build

# Set working directory
WORKDIR /app

# Copy package files for better caching
COPY package.json package-lock.json ./

# Install dependencies
RUN npm ci

# Copy application code
COPY . .

EXPOSE 3000 
CMD ["npm", "start"]