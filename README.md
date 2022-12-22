
# 📃IPWhitelist
A Proxy wide IP Whitelist for Velocity. Heavely based on james090500 work https://github.com/james090500/VelocityWhitelist

## Permissions
| Permission | Purpose |
|--|--|
| `ipwhitelist.admin` | Needed for all `/ipwhitelist` commands |
| `ipwhitelist.bypass` | Can bypass whitelist even if not in list |

## Commands
| Command | Response |
|--|--|
| `/ipwhitelist` | Info command |
| `/ipwhitelist on` | Turn the whitelist on |
| `/ipwhitelist off` | Turn the whitelist off |
| `/ipwhitelist add <ip>` | Add a ip to the whitelist |
| `/ipwhitelist remove <ip>` | Remove a ip from the whitelist |
| `/ipwhitelist reload` | Reload the whitelist config |

## Config
```toml
# Whether the whitelist should be on or off
enabled = true
# The message to be shown on user disconnect
message = "&cWhitelist enabled!"
```
