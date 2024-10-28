# Project Manager for construction companies

## General description

This application helps the construction companies to manage their projects. It consists of allocating employees and materials necessary for a building project. There are 3 types of employees: managers, workers, and engineers. Each project must have a unique engineer and a manager. The workers can not work on more than one project at the same time. The manager's role is to ensure that the conditions fit for each construction project. Besides that, the manager has to allocate the needed materials to each project, in order to have an efficient organization of resources.

### Usage

- The app allows the managers to plan and organize building projects. Each manager is allowed to manage more than one project. A project can be created **only** if there are unallocated/enough employees.
- The managers are not allowed to allocate an engineer to more projects. Besides that, the workers **can not work on more projects at the same time**. If the manager tries to incorrectly allocate the employees, a warning will be shown and the allocation will not take place.
- To be efficient, the managers have to allocate to the project the needed materials too. **The inventory needs to be checked** in order to see if the materials are in stock. If the manager tries to incorrectly allocate materials, a warning will be shown and the allocation will not take place.
- The manager has to create a contract with the client. The client can either want a construction project or a maintenance project. **The contract has to be according to the type of the project**. 

### Functionality

1. **Project Management:** Add, delete, or modify construction projects.
2. **Employees Allocation** Allocate the needed employees to a project.
3. **Material Allocation** Allocate the needed materials to a project.
4. **Material Inventory** Monitor the inventory of materials.
5. **Client Management** Add, delete, or modify a client.
6. **Create Contract** Creates a type of contract (ConstructionContract or MentenanceContract).
7. **Visualize Projects** Shows a project's data, like workers, the engineer, and the manager.
8. **Unallocated Employees** Shows the employees that are not allocated to any project.

![alt text](https://github.com/Octavasds/Construction-Projects-Manager/blob/main/image.png)
