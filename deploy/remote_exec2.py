import paramiko, sys, time, os

host = "223.4.251.0"
port = 22
user = "root"
password = "Majunhao233."

cmd = sys.argv[1] if len(sys.argv) > 1 else "echo hello"

try:
    client = paramiko.SSHClient()
    client.set_missing_host_key_policy(paramiko.AutoAddPolicy())
    client.connect(host, port=port, username=user, password=password, timeout=15, look_for_keys=False, allow_agent=False)
    
    # 打开会话，不传递本地环境变量
    transport = client.get_transport()
    channel = transport.open_session()
    channel.settimeout(600)
    
    # 使用 env -i 完全清除环境变量，只设置必需的 PATH
    clean_cmd = (
        'env -i HOME=/root '
        'PATH=/usr/lib/jvm/java-17-openjdk-17.0.20.0.8-1.1.0.2.1.al8.x86_64/bin:/usr/local/maven/bin:/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin '
        'bash -c ' + repr(cmd)
    )
    
    channel.exec_command(clean_cmd)
    
    stdout = b""
    stderr = b""
    while True:
        if channel.exit_status_ready() and not channel.recv_ready() and not channel.recv_stderr_ready():
            break
        if channel.recv_ready():
            stdout += channel.recv(4096)
        if channel.recv_stderr_ready():
            stderr += channel.recv_stderr(4096)
        time.sleep(0.1)
    
    exit_status = channel.recv_exit_status()
    
    if stdout:
        print(stdout.decode('utf-8', errors='replace').rstrip())
    if stderr:
        print(stderr.decode('utf-8', errors='replace').rstrip(), file=sys.stderr)
    
    print(f"[EXIT={exit_status}]")
    client.close()
except Exception as e:
    print(f"ERROR: {e}", file=sys.stderr)
    sys.exit(1)
