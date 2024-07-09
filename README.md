Exploring Software Refactoring Practices: Diversity,Evolution and External Attributes
=================================
This repository hosts the replication package for our research paper, titled "Exploring Software Refactoring Practices: Diversity,Evolution and External Attributes". It includes the necessary code and datasets to replicate our datasets and further explore the dataset we have compiled and analyzed.

## Directory Overview
 1. The content in refactoringInformation.zip includes all the commit information collected in this experiment, including refactoring in commit, commit message, commit date, etc.


 2. The experiment folder contains all experimental data related to the results of this paper.
      - The data in the alltye folder is the count of refactoring in all projects. The alltype folder is divided into Apache and Eclipse folders according to different systems.
      - The pattern folder is divided into two categories:Eclipses and Apache. Each file stores the refactoring patterns that collected in the refactoring history of each project.
      - The distributionOfPattern folder contains the distribution of various refactoring patterns.
      - The numberOfRefactoringCommitPerMonth folder contains the number of refactoring and regular commit per month in the development process of all projects.
      - The refactoryPerYear folder contains the types and quantities of refactoring for each year in the development process of all projects.
      - The monthRAS folder contains the number of modified files that involve refactoring per month in the development process.
      - The bug-related folder contains the statistical results of bug proneness of refactoring operations.
      - The EvolutionaryVolatility folder contains all refactorings that exhibit evolutionary volatility in the project. Each table displays the code elements modified during refactoring; the two modification times for this code element,;two refactorings and the tags for the two refactorings (the tag consists of the commitId and the order of the refactoring within the commit). The column named right represents the refactoring that first modified this code element, while the column named left represents the refactoring that subsequently modified it.
      - The "bugRelatedAndVolatility" folder contains the data of bug proneness of refactoring operations that exhibit evolutionary volatility.
3. The Src folder contains all the code scripts utilized for data collection.
