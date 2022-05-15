#include <iostream>
#include <cstdio>
#include <sstream>
#include <random>
#include "name.h"
#include "class_name.h"
#include "data.h"
using namespace std;

#define SEX_SIZE 2
const char* Sex[] = {"男生", "女生"};

const char* get_sex();
const char* get_name();
const char* get_data();
const char* get_class_name();