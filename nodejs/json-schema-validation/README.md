# Schema Validation with JSON Schemas

## Introduction

MongoDB 3.6 includes support for JSON Schema Validation, based on the [JSON Schema draft specification](http://json-schema.org/). The support of JSON Schema extends the capabilities of the [Document Validation](https://docs.mongodb.com/manual/core/document-validation/) feature introduced in MongoDB 3.2.

As of MongoDB 3.2, [Document Validation](https://docs.mongodb.com/manual/core/document-validation/) can be used to require that any documents inserted or updated follow a set of validation rules expressed using MongoDB query syntax. This allows for the definition of required content. However, it has no mechanism to restrict users from adding documents containing fields beyond those specified in the validation rules, nor for specifying completely required content, including what is embedded inside arrays.

## Labs

The following labs are currently available:

* [(Part 1) Getting Started with Schema Validation](./HOL-PART1.md)
* [(Part 2) Digging deeper into Schema Validation: sub-documents and additional fields restrictions](./HOL-PART2.md)
* [(Part 3) Advanced topic: array validation](./HOL-PART3.md)
* [(Part 4) Advanced topic: property and schema dependencies](./HOL-PART4.md)

Part 1 and 2 of this lab are available thanks to [Asya Kamsky](https://github.com/asya999)'s original content on this topic. Part 3 and 4 of this hands-on lab are available thanks to [Ken W. Alger](https://github.com/kenwalger)'s contributions.