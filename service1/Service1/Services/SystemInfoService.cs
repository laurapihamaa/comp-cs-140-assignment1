class SystemInfoService
{
    public int GetUptime()
    {
        return Environment.TickCount;
    }

    public int GetFreeDiskInRoot()
    {
        var drive = new System.IO.DriveInfo("/");
        return (int)(drive.AvailableFreeSpace / (1024 * 1024));
    }
}