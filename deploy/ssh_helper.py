#!/usr/bin/env python3
"""SSH helper for Alibaba Cloud ECS deployment."""
import sys
import paramiko
import time

HOST = "223.4.251.0"
USER = "root"
PASS = "Majunhao233."

def run_cmd(cmd, timeout=120):
    """Execute a command on the remote server and print output."""
    client = paramiko.SSHClient()
    client.set_missing_host_key_policy(paramiko.AutoAddPolicy())
    client.connect(HOST, username=USER, password=PASS, timeout=15)
    stdin, stdout, stderr = client.exec_command(cmd, timeout=timeout)
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
    if len(sys.argv) < 2:
        print("Usage: python ssh_helper.py 'command'")
        sys.exit(1)
    cmd = sys.argv[1]
    timeout = int(sys.argv[2]) if len(sys.argv) > 2 else 120
    exit_code = run_cmd(cmd, timeout)
    sys.exit(exit_code)
