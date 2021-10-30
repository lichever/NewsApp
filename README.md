# Insurance Company Email Automation

* To run this assigment, please select the Intellij "Edit configurations" when under the class **Processor** and then input your command line arguments in the **Program arguments** section. Now, you can run the main function in the class **Processor**.
* All the provided email and letter templates, csv file, and **my templates** are in the "docs" directory under the root directory.
* The UML Class diagram is in the UML directory under the root directory. 
*	The relative path to the project root is used in this assignment which let the path could be platform independent. So, please use the relative directory path in the commands "--output-dir <path>", "--email-template <file>", "--letter-template <file>", and "--csv-file <path>".
* Note that the output path for the command "--output-dir <path>" cannot be the same in the second run, which would throw an exception. So, please specify different directory names in the multiple runs or delete the previous one before the next run.
* Examples for successful email and letter generation:
  1. --email --email-template docs/email-template.txt --output-dir emails --csv-file docs/insurance-company-members.csv
  2. --letter --letter-template docs/letter-template.txt --output-dir letters --csv-file docs/insurance-company-members.csv



