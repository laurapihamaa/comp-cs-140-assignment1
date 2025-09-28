# Use the official .NET SDK image for building the app
FROM mcr.microsoft.com/dotnet/sdk:8.0 AS build

ENV ASPNETCORE_URLS=http://+:8199

# Set the working directory
WORKDIR /src

# Copy the .NET project files
COPY ./Service1.csproj .

# Restore the dependencies (via NuGet)
RUN dotnet restore ./Service1.csproj

# Copy the rest of the app's source code
COPY . .

# Publish the .NET application to the /out directory
RUN dotnet publish ./Service1.csproj -c Release -o /app/publish

# Use the official .NET Runtime image for running the app
FROM mcr.microsoft.com/dotnet/aspnet:8.0 AS base

ENV ASPNETCORE_URLS=http://+:8199

# Set the working directory
WORKDIR /app

# Copy the published files from the build image
COPY --from=build /app/publish .

# Expose the port your .NET app will run on
EXPOSE 8199

# Run the .NET app
ENTRYPOINT ["dotnet", "Service1.dll"]

