#!/usr/bin/env python3
"""Deploy script executor for Alibaba Cloud ECS."""
import sys
import paramiko

HOST = "223.4.251.0"
USER = "root"
PASS = "Majunhao233."

def run_script(script_content, timeout=600):
    """Upload and execute a script on the remote server."""
    client = paramiko.SSHClient()
    client.set_missing_host_key_policy(paramiko.AutoAddPolicy())
    client.connect(HOST, username=USER, password=PASS, timeout=15)
    
    # Write script to remote file
    sftp = client.open_sftp()
    with sftp.open('/tmp/deploy.sh', 'w') as f:
        f.write(script_content)
    sftp.close()
    
    # Execute script
    stdin, stdout, stderr = client.exec_command(f'bash /tmp/deploy.sh 2>&1', timeout=timeout)
    out = stdout.read().decode('utf-8', errors='replace')
    err = stderr.read().decode('utf-8', errors='replace')
    exit_code = stdout.channel.recv_exit_status()
    client.close()
    
    if out:
        print(out, end='')
    if err:
        print(err, end='', file=sys.stderr)
    print(f"\n[EXIT={exit_code}]")
    return exit_code

if __name__ == "__main__":
    script_file = sys.argv[1]
    timeout = int(sys.argv[2]) if len(sys.argv) > 2 else 600
    with open(script_file, 'r', encoding='utf-8') as f:
        content = f.read()
    exit_code = run_script(content, timeout)
    sys.exit(exit_code)
