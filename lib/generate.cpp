#include "header.h"

int class_number, target_number;

int main(int argc, char * argv[]) {
    double x, y;
    
    stringstream stream;
    stream << argv[1];
    stream >> target_number;
    
    random_device r1{}, r2{}, r3{};
    mt19937 g1{r1()}, g2{r2()}, g3{r3()};
    
    
    switch(target_number) {
        case 200: {
            x = 70, y = 6;
        } break;
        case 500: {
            x = 70, y = 7;
        } break;
        case 2000: {
            x = 70, y = 15;
        } break;
        case 5000: {
            x = 68, y = 17;
        } break;
        case 20000: {
            x = 50, y = 25.67;
        } break;
        case 50000: {
            x = 55, y = 27;
        } break;
        case 200000: {
            x = 52, y = 27.3;
        } break;
        case 500000: {
            x = 48, y = 28;
        } break;
        default: 
            x = 44, y = 28.6;
    }
    class_number = target_number / 50;
   
    normal_distribution<> d{x, y};
            
    for (int i = 0; i < target_number; ) {
        int a = round(d(g1)), b = round(d(g2)), c = round(d(g3));
		
        if(a >= 0 &&  b >= 0 && c >= 0 && a <= 100 && b <= 100 && c <= 100) {
            i++;
            const char * class_name = get_class_name();
            printf("INSERT INTO `stu` (name, sexual, birthday, classid, math, java, pe, sumscore) ");
            printf("value('%s', '%s', '%s', '%s', %d, %d, %d, %d)\n", 
				get_name(), get_sex(), get_data(), class_name, a, b, c, a + b + c);
        }
    }
    
    
    return 0;
}

inline const char* get_sex() {
	random_device r{};
	mt19937 g{r()};
	uniform_real_distribution<> dis(0, SEX_SIZE);
	unsigned int t = dis(g);
	
	return Sex[t];
}

inline const char* get_name() {
	random_device r{};
	mt19937 g{r()};
	uniform_real_distribution<> dis(0, NAME_SIZE);
	unsigned int t = dis(g);
	
	return Name[t];
}

inline const char* get_data() {
	random_device r{};
	mt19937 g{r()};
	uniform_real_distribution<> dis(0, DATA_SIZE);
	unsigned int t = dis(g);
	
	return Data[t];
}

inline const char* get_class_name() {
	random_device r{};
	mt19937 g{r()};
	uniform_real_distribution<> dis(0, class_number);
	int t = dis(g);
	t = (t % class_number + class_number) % class_number;
	
	return Class_name[t];
}