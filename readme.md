# NOTE: This branch doesn't have a target directory:
so if you want to run the project:
## RUN `mvn clean compile install` first before doing anything

Thank you. 😃

### Rationale:
- Java projects should be compiled on the user's specific machines
  - This makes it so walang problems with running the project on different OSes, machines blah blah
- Unnecessary files are not included in the repo making it more clean to look at and less bloated
  - All of this is achieved through .gitignore