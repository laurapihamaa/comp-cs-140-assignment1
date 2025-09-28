using Microsoft.AspNetCore.Http.HttpResults;

var builder = WebApplication.CreateBuilder(args);

builder.Services.AddSingleton<SystemInfoService>();

// Add services to the container.
// Learn more about configuring Swagger/OpenAPI at https://aka.ms/aspnetcore/swashbuckle

var app = builder.Build();

app.UseHttpsRedirection();


app.MapGet("/status", async (SystemInfoService systemInfo) =>
{
    var state = new State
            (
                systemInfo.GetUptime(),
                systemInfo.GetFreeDiskInRoot()
            );

    Console.WriteLine(state.state);

    var client = new HttpClient();
    client.BaseAddress = new Uri("http://storage:8080");
    var responseStorageSave = await client.PostAsJsonAsync("/log", state);

    if (!responseStorageSave.IsSuccessStatusCode)
    {
        return Results.Problem("Failed to log state to storage service");
    }

    await File.AppendAllTextAsync("/vstorage/state.log", state.state);

    var serviceClient = new HttpClient();
    serviceClient.BaseAddress = new Uri("http://service2:3002");
    var serviceResponse = await serviceClient.GetAsync("/status");
    var message = await serviceResponse.Content.ReadAsStringAsync();

    if (!serviceResponse.IsSuccessStatusCode)
    {
        return Results.Problem("Failed to get state from service2");
    }

    return Results.Ok(state.state + message);
});

app.MapGet("/log", async() =>
{

    var client = new HttpClient();
    client.BaseAddress = new Uri("http://storage:8080");
    var log = await client.GetAsync("/log");

    if (!log.IsSuccessStatusCode)
    {
        return Results.Problem("Failed to get log from storage service");
    }

    return Results.Ok(await log.Content.ReadAsStringAsync());
});

app.Run();

record State(int Uptime, int FreeDisk)
{
    public string state => string.Format("Timestamp1: uptime {0}, free disk in root {1} MB", Uptime, FreeDisk);
}
