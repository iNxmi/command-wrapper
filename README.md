### docker-compose.yml
```yaml
services:
  command-wrapper:
    image: inxmi/command-wrapper:latest
    restart: unless-stopped
    
    environment:
      TOKEN: <TOKEN> # DISCORD_BOT_TOKEN
      INSTANCES: |- # List of docker compose stacks -> path:channel_id
        <PATH>:<CHANNEL_ID>
        
    volumes:
      - /var/run/docker.sock:/var/run/docker.sock # necessary to execute docker compose command sin container for host machine
      - /dir/<PATH>:/instances/<PATH>:ro # mount the docker compose stack directory in the appropriate path defined earlier as read only
```