# Ansible role `install_ai`

An Ansible role for install patch using AutoInstaller tool. 

Operating procedure:
* Unpack patch into `install_ai_netcracker_home` directory.
* Modify configuration file(s).
* Start AutoInstaller tool by executing install.sh script.

##Role Variables
| Variable                          | Required | Type   | Default | Description                                                                                                                                                                                                                                      |
| :---                              | :---     | :---   | :---    | :---                                                                                                                                                                                                                                             |
| `install_ai_patch_path`           | Yes      | path   | -       | Absolute path to AutoInstaller zip package.                                                                                                                                                                                                      |
| `install_ai_netcracker_home`      | Yes      | path   | -       | Absolute path to NETCRACKER_HOME directory. AutoInstaller should be installed in this path.                                                                                                                                                      |
| `install_ai_config_files`         | No       | list   | []      | List of properties files.If patch contains 'etalon_config_###.properties' then files names in list should be in format 'config_###.properties'.                                                                                                  |
| `install_ai_args`                 | No       | string | ''      | Extra arguments for install.sh script.                                                                                                                                                                                                           |
| `install_ai_async`                | No       | int    | 7200    | Maximum runtime for AutoInstaller execution.                                                                                                                                                                                                     |
| `install_ai_poll`                 | No       | int    | 30      | Specifies how frequently Ansible will poll AutoInstaller for status.                                                                                                                                                                             |
| `install_ai_backup_config`        | No       | bool   | No      | Set this parameter to 'true' to backup the original file.                                                                                                                                                                                        |
| `install_ai_preinstall_check`     | No       | string | 'skip'  | Specifies preinstallation check mode. Possible values: <ul><li>[rbm - check if RBM patch is installed.](readme.md#RBM_patch) </li><li>skip - do not check anything.</li></ul>                                                                                            |
| `install_ai_recreate_configs`     | No       | bool   | No      | By default AutoInstaller config files are updated only with changed parameters (and never passwords). If need recreate the files from etalon set this parameter to 'true'.                                                                       |
| `install_ai_predefined_properties`| No       | dict   | {}      | Dict for paramaters which should be updated in 'install_ai_config_files'. Predefined parameters can be placed inside roles/playbooks.                                                                                                            |
| `install_ai_additional_properties`| No       | dict   | {}      | Dict for paramaters which should be updated in 'install_ai_config_files'. Additional parameters can be provided in any external configuration (group_vars, extra vars, etc.). Additional properties has greater precedence than predefined ones. |
| `install_ai_force`                | No       | bool   | No      | Set this parameter to 'true' if patch installation should happend even if it is already installed.                                                                                                                                               |



###RBM_patch
<details>

<summary>RBM patch variables</summary>

  #### RBM patch
| Variable                     | Required | Type   | Default       | Description                                      |
| :---                         | :---     | :---   | :---          | :---                                             |
| `rbm_inf_user_name`          | Yes      | string | -             | The database username to connect to the database |
| `rbm_inf_user_password`      | Yes      | string | -             | The password to connect to the database          |
| `rbm_be_oracle_host`         | No       | string | localhost     | The host of the database                         |
| `rbm_be_oracle_port`         | No       | int    | 1521          | The listener port to connect to the database     |
| `rbm_be_oracle_service_name` | No       | string | database_name | The service_name to connect to the database      |
| `rbm_db_connection_type`     | No       | string | service_name  | Description<ul><li>Specifies DSN connection string. Full TNS will be taken from client tnsnames.ora file.</li><li>If C(sid) then connection string - `(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(HOST=<host>)(PORT=<port>))(CONNECT_DATA=(SID=<service_name>)))` </li><li>If C(service_name) then connection string - `(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(HOST=<host>)(PORT=<port>))(CONNECT_DATA=(SERVICE_NAME=<service_name>)))` </li><li>If C(tnsnames) then connection string - `<service_name>`</li></ul> Choises:<ul><li>sid</li><li>service_name</li><li>tnsnames</li></ul> |
| `ORACLE_HOME`                | No       | path   | -             | Path to Oracle client home directory             |

</details>

##Examples
```yaml
- name: Install Patch
  include_role:
    name: install_ai
  vars:
    install_ai_netcracker_home: "{{ tbapi_netcracker_home }}"
    install_ai_patch_path: "{{ tbapi_kits_dir }}/{{ tbapi_patch_file }}"
    install_ai_config_files: "{{ tbapi_config_files }}"
    install_ai_predefined_properties: "{{ tbapi_patch_properties }}"
    install_ai_args: "{{ tbapi_patch_args }}"
    install_ai_async: "{{ tbapi_patch_async }}"
    install_ai_poll: "{{ tbapi_patch_poll }}"

- name: Install RB Core patches
  include_role:
    name: install_ai
  vars:
    install_ai_netcracker_home: "{{ infinys_dir }}"
    install_ai_patch_path: "{{ item }}"
    install_ai_args: "{{ install_args }}"
  with_items: "{{ rbm_core_patches  }}"
```
