# Use an OpenJDK image with Leiningen installed for building and running Clojure apps
FROM clojure:openjdk-11-lein-2.9.1

# Set the working directory in the container
WORKDIR /app

# Copy the Clojure project files into the container
COPY . /app

# Install Leiningen dependencies
RUN lein deps

# Build the Clojure project
RUN lein uberjar

# Expose the port your Clojure service will run on
EXPOSE 8080

# Command to run your Clojure app (assuming you have a "run" task or "-main" function)
CMD ["lein", "run"]
