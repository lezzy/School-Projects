#include <cstdlib>
#include <iostream>
#include <string>
#include <fstream>
#include <stdio.h>
#include <sstream>
#include <string.h>
#include <exception>
#include <new>
#include <cctype>
#include <cstring>

using namespace std;
// Name        : FileSystem53.cpp
// Author      : Lesmond Chow, Colin Ma, Kylie Cheng
// Version     : cr & op finished, rd, wr not finished
// Copyright   : Your copyright notice
// Description : First Project Lab
//============================================================================


class FileSystem53 {
    public:
int B;  //Block length
int k;  //Number of blocks for descriptor table
char** desc_table;  // Descriptor Table (in memory).

// Filesystem format parameters:
int FILE_SIZE_FIELD;     /* Size of file size field in bytes. Maximum file size allowed in this file system is 192.*/
int ARRAY_SIZE;          /* The length of array of disk block numbers that hold the file contents.*/
int DESCR_SIZE;
int MAX_FILE_NO;        /* Maximum number of files which can be stored by this file system. */
int MAX_BLOCK_NO;       /* Maximum number of blocks which can be supported by this file system.*/
int MAX_BLOCK_NO_DIV8;
int MAX_FILE_NAME_LEN;  /* Maximum size of file name in byte. */
int MAX_OPEN_FILE;       /* Maximum number of files to open at the same time.*/
int FILEIO_BUFFER_SIZE; /* Size of file io buffer*/
int MAX_FILE_SIZE;
int MAX_BLOCK_SIZE;
 /* Variables added*/
int sizeLdisk;
char** ldisk;
int* ptrPos;
int fIndex;
char* bm;
char* mem_area;
//int fSz;   int open_file_no;
struct File {
   int fileLen;
   char* fileCnt;
   };
struct OFT_entry{
char* buffer;
int current_block;
int current_position;
int file_desc_index;
};
OFT_entry *OFT;
File arrFile[14];
int* fPos;
class endofFile {};


/* Constructor of this File system.*/
FileSystem53()
{
/* Initializing all variables */
FILE_SIZE_FIELD = 1;
ARRAY_SIZE = 3;
DESCR_SIZE = FILE_SIZE_FIELD+ARRAY_SIZE;
MAX_FILE_NO = 14;
MAX_BLOCK_NO = MAX_BLOCK_SIZE = 64;
MAX_BLOCK_NO_DIV8 = MAX_BLOCK_NO/8;
MAX_FILE_NAME_LEN = 32;
MAX_OPEN_FILE = 3;
FILEIO_BUFFER_SIZE = 64;
MAX_FILE_SIZE = 192;
sizeLdisk = 0;
k = 7;
this->B = 64;
this->sizeLdisk = 64;
fIndex = -1;
try {
OFT = new OFT_entry[3];
for (int i = 0; i < 3; i++) {
    OFT[i].current_position = -1;
    OFT[i].current_block = -1;
    OFT[i].file_desc_index = -1;
      OFT[i].buffer = new char[65];
        }
bm = new char[65];
mem_area = new char[193];
/*Initialize ldisk*/
ldisk = new char*[65];
        for (int i = 0; i < sizeLdisk; i++) {
            ldisk[i] = new char[65];
            //for (int k=0; k < 64; k++) {
              //  ldisk[i][k]->" ";
            }
desc_table = new char*[14];
for (int i = 0; i < 14; i++) {
    desc_table[i] = new char[65];
}
ptrPos = new int[3];
fPos = new int[14];
for (int i = 0; i < 14; i++) {
arrFile[i].fileLen = 0;
arrFile[i].fileCnt = new char[65];
} 
for (int k=0; k < 64; k++) {
    *arrFile[0].fileCnt = 0;
}
//For the directory contents, the char array [0] to [9] is the name of a file, and [10] is the index number.
 // arrFile[0].fileCnt[0] to arrFile.fileCnt[10] is for directory name&index
//arrFile.fileCnt[11] to arrFile.fileCnt[21] is for file index 1, arrFile.fileCnt[22] to arrFile.fileCnt[32]
//is for file index 2 until arrFile.fileCnt[55] to arrFile.fileCnt[65] is for file 5...
}

catch (bad_alloc) {
      std::cout << "Insufficient memory\n";
}
OFT[0].current_block = 0;
OFT[0].file_desc_index = 0;
OFT[0].current_position = 0;
for (int i = 0; i < 64; i++){
bm[i] = 48;
}
         bm[0] = 49;  
         bm[1] = 49;
         bm[15] = 49;
         bm[16] = 49;
         bm[17] = 49;

}
~FileSystem53(){
                delete [] bm;
                for (int i = 0; i < 64; i++) {
                    delete [] ldisk[i];                      
                }          
             for (int i = 0; i < 14; i++) {
                    delete [] desc_table[i];
                    }
             for (int i = 0; i < 14; i++) {
                 delete [] arrFile[i].fileCnt;
                 }
             delete [] OFT;
             for (int i = 0; i < 3; i++) {
                 delete [] OFT[i].buffer;
                 }
             delete [] ptrPos;
}

void save(){
  ofstream outFile;
  outFile.open("dsk.txt");
  for (int i = 0; i<64; i++){
for (int j = 0; j<64; j++){
  outFile << ldisk[i][j];
}
outFile << "\n";
  }
  outFile.close();
}

void restore(){
  ifstream inFile;
  inFile.open("c:\\dsk.txt");
  string line;
  int counter = 0;
  std::cout << inFile.is_open() << std::endl;
  if (inFile.is_open()){
  while (getline(inFile, line)){
        if (line.empty()){
        throw;
        }
std::cout << line.size() << endl;
  int first, second;
   char *a = new char[line.size()+1];
    while (*a != '\0'){
      first = counter/64;
      second = counter%64;
      ldisk[first][second] = *a;
      a=a+1;
      counter++;
  }
    delete [] a;
}
}
inFile.close();
   }

void read_block(int i, char *p){
     try {
if (i > 63||i < 0) throw "Out of range";
}
catch (char * str){
    cout << "Exception: " << str << endl;
    exit(1);
}
memcpy (p, &ldisk[i], 64);
}
void write_block(int i, char *p){
try {
if (i > 63||i < 0) throw "Out of range";
}
catch (char * str){
    cout << "Exception: " << str << endl;
    exit(1);
}
//char* x = new char[64];
//memcpy(&x[i], p, 64);
memcpy(&ldisk[i], p, 64);
}

/* Open File Table(OFT).*/
void OpenFileTable()
{
OFT = new OFT_entry[MAX_OPEN_FILE];
}

/* Allocate open file table */
int find_oft()
{int i, ret;
    for (i = 0; i < 3; i++) {
        if (OFT[i].current_position == -1) /*if it's still -1 then it hasn't been set yet */
        {  OFT[i].current_block = 0;
           OFT[i].current_position = 0; /*indicate it is now taken */
           OFT[i].file_desc_index = 0;
           ret = i;
           break;
           }
    }
    if (i == 4)
    {
     return -2; //All OFTs occupied
     }
     else if (i < 3) {
     return ret;
     }
     else
     return -2;
}

/*Deallocate*/
void deallocate_oft(int index){
     try {
if (index > 2||index < 0) throw "Out of range";
}
catch (char * str){
    cout << "Exception: " << str << endl;
    exit(1);
}
 OFT[index].current_position=  -1;/*with the deallocated element reset it to 5.*/
 OFT[index].current_block = -1;
 OFT[index].file_desc_index = -1;
}
/* Format file system.*/
    void format() {
 bm[1] = 49; 
 bm[15] = 49;
 bm[16] = 49;
 bm[17] = 49;
         }

/* Read descriptor */
char* read_descriptor(int no) {
try {
if (no > 13||no < 0) throw "Out of range";
}
catch (char * str){
    cout << "Exception: " << str << endl;
    return '\0';
}
return desc_table[no];
}

  /* Clear descriptor */
void clear_descriptor(int no) {
try {
if (no > 13||no < 0) throw "Out of range";
}
catch (char * str){
    cout << "Exception: " << str << endl;
    exit(1);
}
delete [] desc_table[no];
bm[no] = 48;
        }

        /* Write descriptor*/
void write_descriptor(int no, char* desc) {
int i = 0;
while (desc[i] != '\0') {
      desc_table[no][i] = desc[i];
      i++;
}
}

/* Search for an unoccupied descriptor. */
int find_empty_descriptor() {
int i;
for (i = 2; i < 15; i++) {
      if (bm[i] == 48) {
       break;
      }
}
if (i < 0 || i > 14) {
    return 999;
}
else if (1 < i < 14) {
return i;
}
}

/* Search for an unoccupied block. */
int find_empty_block() {
    int ret = -1;
    int i;
for (i = 18; i < 64; i++) {
    if (bm[i] == 48) {
       ret = i;
       break;
    }
}
if (18 <= ret < 64)
return ret;
else if (i == 64)
return -1;
else
return -1;
}
//Use arrFile to read
int fgetc(int index) {
    if (arrFile[index].fileCnt[ptrPos[index]]=='\0')
    return EOF;
    else {
    int a = (arrFile[index].fileCnt[ptrPos[index]])+48; 
  return a;   
  }
}
int fputc(int c, int index) {
    //if no error
    arrFile[index].fileCnt[fPos[index]] = 'c';
    return 'c';
    //else return -2;
}
bool feof(int index);
/* Search for a file */
int search_dir(int index, string symbolic_file_name)
{
    int fiIndex = -1;
    int ind, k;
    for (ind = 11; ind < 155; ind = ind + 11) { //Find file name in the directory. 
        for (k = 0; k < symbolic_file_name.length(); k++){
            if (symbolic_file_name[k] != arrFile[0].fileCnt[ind+k]) {
                break;
                }
        }
        if (k==symbolic_file_name.length()) {
           fiIndex = (ind)/10;
           break;
           }
    }
  std::cout << "file index=" << fiIndex << std::endl;
     if (ind == 155)
        return -1; //File not found
     else {
     int j;
     for (j = 0; j < 3; j++) {
         if (OFT[j].file_desc_index == fiIndex) {
         cout << "File already open! \n";
         break;
         }
     }
     if (j < 3) {
        return ind; //starting position of the regular file within the directory file
        }
     }
}
int delete_dir(int index, int start_pos, int len){
                   lseek (index, start_pos);
        for (int i = start_pos; i < len; i++) { //Find file name in the directory. If not duplicate, add to directory.
            *(arrFile[0].fileCnt+i) = '\0';
            }
}   

/*File deleted will have an empty descriptor for a later file. That descriptor would be the new file index and descriptor number.*/
int create(string symbolic_file_name) {
int k;    
for (int ind = 11; ind < 155; ind = ind + 11) { /*Find file name in the directory. If not duplicate, add to directory.*/
    for (k = 0; k < symbolic_file_name.length(); k++){
        if (symbolic_file_name[k] != arrFile[0].fileCnt[ind+k]) {
                break;
        }
    }
    if (k == symbolic_file_name.length()) {                       
            std::cout << "File already exists!\n";
            return -2;
    }  
}
int a = find_empty_descriptor() - 1; //update descriptor block
if (a==998){
return -1; // if no empty descriptor
}
else {
fIndex = a;
bm[a+1] = 49;
std::cout << "empty descriptor number: " << a+1 << std::endl;
std::cout << "bm[file descriptor]=" << bm[a+1] << std::endl;
write_descriptor(fIndex, "0\0\0\0");
std::cout << "file size= " << desc_table[fIndex][0] << endl;
int i;
for (i=0; i < symbolic_file_name.length(); i++) {
*(OFT[0].buffer+i) = symbolic_file_name[i];
char a = symbolic_file_name[i];
arrFile[0].fileCnt[i + (fIndex)*11] = a;
}
std::cout << "file name=" << arrFile[0].fileCnt[i+(fIndex)*11] << std::endl;
std::cout << "buffer content=" << OFT[0].buffer << std::endl;
arrFile[0].fileCnt[(fIndex+1)*11-1] = fIndex + 48;
std::cout << "file index=" << arrFile[0].fileCnt[(fIndex+1)*11-1] << std::endl;
return 0;
}
}
                
// Open file with file name function:
int open(string symbolic_file_name) {
    int fiIndex = -1;
    int ind, k;
    for (ind = 11; ind < 155; ind = ind + 11) { //Find file name in the directory. 
        for (k = 0; k < symbolic_file_name.length(); k++){
            if (symbolic_file_name[k] != arrFile[0].fileCnt[ind+k]) {
                break;
                }
        }
        if (k==symbolic_file_name.length()) {
           fiIndex = (ind)/10;
           break;
           }
    }
  std::cout << "file index=" << fiIndex << std::endl;
     if (ind == 155)
        return -1; //File not found
     int j;
     for (j = 0; j < 3; j++) {
         if (OFT[j].file_desc_index == fiIndex) {
         cout << "File already open! \n";
         break;
         }
     }
     if (j == 3) {
           int k = find_oft();
           if (0 < k < 3) {
           OFT[k].file_desc_index = fiIndex;
           OFT[k].current_block = 0;
           OFT[k].current_position = 0;
           std::cout << "OFT current position=" << OFT[k].file_desc_index << std::endl;
           std::cout << "OFT index=" << k << std::endl;
           }
     return k; //return the number of an empty OFT or -2 if all occupied
     }
}
/* Setting new read/write position function:*/
int lseek(int index, int pos)
{
if (OFT[index].current_position == -1)
   return -1;
else {
     if (pos > MAX_FILE_SIZE){
     OFT[index].current_block = 2;
     pos = MAX_FILE_SIZE; 
     }
     else if (0 < pos < 64){
     OFT[index].current_block = 0;
     OFT[index].current_position = pos;
     }
     else if (65 <= pos <= 128) {
     OFT[index].current_block = 1;
     OFT[index].current_position = pos-64;
     }
     else if (129 <= pos <= 192) {
     OFT[index].current_block = 2;
     OFT[index].current_position = pos-128;
     }
     std::cout << "OFT current position=" << OFT[index].current_position << std::endl;
     std::cout << "OFT current block=" << OFT[index].current_block << std::endl;
     return 0;
}
}

/* File Read function:*/
int read(int index, char* mem_area, int count) //for mem_area reading and writing 
{
if (OFT[index].current_position==-1) {
cout << "File hasn't been open\n";
return -1;
}
std::cout << "OFT[index].file_desc_index=" << OFT[index].file_desc_index << std::endl;
std::cout << "file size=" << desc_table[OFT[index].file_desc_index][0] << std::endl;
if (desc_table[OFT[index].file_desc_index][0]==48) {
             std::cout << "File Empty\n";
             return -2;
             }
if (0 < index < 3) {
        std::cout << "OFT Current position=" << OFT[index].current_position << std::endl;
                                               
         if (0 < count+OFT[index].current_position <= 64) {
               for (int i = 0; i < OFT[index].current_position+count; index++) {
                   char* a;
                   try {
                   a = new char;
                   }
                   catch (bad_alloc) {
                   std::cout << "Insufficient memory\n";
                   }
                   itoa(fgetc(index), a, 10);
                   strcat(mem_area, a);
                   ptrPos[index]++;
                   OFT[index].current_position++;
                   delete a;
                   }
         }
}
if (count <= 192){
return count;
}
else{
return -2;
}
}

/* File Write function:*/
int write(int index, char value, int count){
    if (OFT[index].current_position==-1) {
std::cout << "File hasn't been open\n";
return -1;
}
int k;
std::cout << "OFT[index].file_desc_index=" << OFT[index].file_desc_index << std::endl;
std::cout << "file size=" << desc_table[OFT[index].file_desc_index][0] << std::endl;
if (desc_table[OFT[index].file_desc_index][0]==48){
                                               
int fBlock0 = find_empty_block();
std::cout << "empty block=" << fBlock0 << std::endl;
desc_table[OFT[index].file_desc_index][1] = fBlock0 +48; 
OFT[index].current_block = 0;
   if (64 > count > 0) {
          desc_table[OFT[index].file_desc_index][0]= desc_table[OFT[index].file_desc_index][0]+ count;
          std::cout << "file size=" << desc_table[OFT[index].file_desc_index][0] << std::endl;
          string a;
          
      for (k = 0; k < count; k++) {
         a+=value;
         // itoa(fputc(value+64, OFT[index].file_desc_index), a, 10);
          
             std::cout << "chars to add:" << a << std::endl;
          
      }
     strcpy(arrFile[OFT[index].file_desc_index].fileCnt, a.c_str());
     std::cout << "1st char on ldisk" << ldisk[fBlock0][0] << std::endl;
      std::cout << "descriptor=" << desc_table[OFT[index].file_desc_index][1] << std::endl;
      std::cout << "file content=" << a << arrFile[OFT[index].file_desc_index].fileCnt << std::endl;
      return 0;
   }
   else if (128 >= count >= 65){
        desc_table[OFT[index].file_desc_index][0] += count; 
        //write_block(fBlock1, p);
        return 0;
        }
   else if (192 >= count >= 129) {
        desc_table[OFT[index].file_desc_index][0] += count; 
       /* write_block(fBlock1, p);
        write_block(fBlock2, p);*/
        return 0;
        }
        
   else if (192 < count){
       return -2;
       }
       else {
            return 0;
            }
}
}

//Close file function:
void close(int index)
{
deallocate_oft(index);
}
int deleteFile(string fileName) {
    
    
}
//Directory listing function:
void directory()// Directory stores the file name and file number/the file index/descriptor number of the file
{ int fcounter, i;   
  std::cout << "fcounter= "<< fcounter << std::endl;
  i = 0;
  while (desc_table[i+1][0] == '0' && (i < 14)) {
        char* file_name_array;
        file_name_array = new char[10];
        fcounter = 0;
        //std::cout << file_name_array << "   " << arrFile[0].fileCnt[(i+1)*11+10] << std::endl;
        while (desc_table[i+1][0] == '0' & (fcounter < 10)) {
              strcat(file_name_array, arrFile[0].fileCnt+fcounter+i*11+2);
             // std::cout << file_name_array << "   " << arrFile[0].fileCnt[(i+1)*11+10] << std::endl;
           fcounter++;
      }
      std::cout << file_name_array << "   " << arrFile[0].fileCnt[(i+1)*11+10] << std::endl;
      i++;
      delete [] file_name_array;
  }
  
 }   

};

int main()
{
FileSystem53 file_system;
 std::string command;
 while (true)
 {
       std::cout<<"Please enter shell command: " << std::endl;
       std::getline(std::cin, command);
       std::istringstream iss(command);
       std::string first, second, third, fourth;
       iss >> first >> second >> third >> fourth;
       if (!first.compare("q") || !first.compare("quit"))
       {
          exit(0);//exit shell if they want to quit
       }
       else if (!first.compare("dr") || !first.compare("directory"))
       {
        file_system.directory();
       }
       else if (!first.compare("in"))
       {
       try
       {
        file_system.restore();
        std::cout << "disk restored" << std::endl;
        }
        catch (std::ifstream::failure e)
        {
        std::cout << "disk initialized" << std::endl;
        }
       }
       else if (!first.compare("sv") || !first.compare("save"))
        {
        try
        {
         file_system.save();
         std::cout << "disk saved" << std::endl;
        }
        catch (std::ifstream::failure e)
              {
              std::cout << "error" << std::endl;
        }
       }
/*these set of if statements are for those with 1 or more commands*/
        if (!first.compare("cr") || !first.compare("create"))
        {
           int check = file_system.create(second);
           if (!check)
           {
              std::cout << "file " << second << " created" << std::endl;
              }
           else if (check == -1)
           {
             std::cout << "Error, no space in disk" << std::endl;
             continue;
           }
           else if (check == -2)
          {
                std::cout << "Error, duplicated file" << std::endl;
                continue;
           }
         }
         else if (!first.compare("de") || !first.compare("destroy"))
           {
            int check = file_system.deleteFile(second);
            if (!check)
            {
               std::cout << "file" << second << "destroyed" << std::endl;
            }
            else {
                std::cout << "Error\n";
                continue;
                }
           }
         else if (!first.compare("op") || !first.compare("open"))
              {
               int check = file_system.open(second);
               if (check == -1)
               {
                  std::cout << "Error, file not found" << std::endl;
                  continue;
               }
               else if (check == -2)
               {
                    std::cout << "Error, maximum number of files already opened" << std::endl;
                    continue;
               }
              else
               {
                  std::cout << "file " << second << " opened, index=<" << check <<">" <<std::endl;
               }
}
else if (!first.compare("cl") || !first.compare("close"))
{
int temp;
char file_name_arry[64];
char index_arry[64];
char temp_string[3];
int second_int = atoi(second.c_str());
file_system.close(second_int);
/*file_system.OFT[second_int].file_desc_index
MUST MAKE IMPL TO GET FILE NAME */
for (int i = 0; i < file_system.MAX_OPEN_FILE; i++){
temp = file_system.read(
(*file_system.ldisk + file_system.OFT[0].file_desc_index)[i],
file_system.OFT[0].buffer,
file_system.FILEIO_BUFFER_SIZE); /* calls read on the array of the directory file*/
for (int i = 0; i < file_system.MAX_BLOCK_SIZE; i++){
if (isalpha(file_system.OFT[0].buffer[i])){ /*if it's alphabetic (ie. a letter) then it's the file name*/
temp_string[i] += file_system.OFT[0].buffer[i]; /* add the char together to a string for file name*/
}
if (isdigit(file_system.OFT[0].buffer[i])){ /*if it's a digit, then it's the file desc. index*/
file_name_arry[i] = temp_string[i]; /* stores file name into an array*/
index_arry[i] = *file_system.OFT[0].buffer; // gets the file desc.
 }
}
}
for (int i = 0; i < 64; i++){
if (index_arry[i] == file_system.OFT[second_int].file_desc_index){
std::cout << "file" << file_name_arry[i] << "closed" << std::endl;
continue;
}
}
}
else if(!first.compare("rd") || !first.compare("read"))
{
int second_int = atoi(second.c_str());
int third_int = atoi(third.c_str());
int check = file_system.read(second_int, file_system.OFT[second_int].buffer, third_int);
if (check == -1)
{
std::cout << "Error, file not open" << std::endl;
continue;
}
else if (check == -2)
{
std::cout << "Error, end of file" << std::endl;
continue;

}
else
{
    int i = file_system.OFT[second_int].file_desc_index;
std::cout << third <<  " bytes read:<" << file_system.arrFile[i].fileCnt << ">" << std::endl;
}
}
else if(!first.compare("wr") || !first.compare("write"))
{
int second_int = atoi(second.c_str());
int fourth_int = atoi(fourth.c_str());
char *third_as_char;
try {
third_as_char = new char[third.length()+1];
}
catch (bad_alloc) {
      std::cout << "Insufficient memory\n";
}
third_as_char[third.length()+1] = 0;
memcpy(third_as_char, third.c_str(), third.length());
int check = file_system.write(second_int, *third_as_char, fourth_int);
if (check == -1)
{
std::cout << "Error, file not open" << std::endl;
continue;
}
else if (check == -2)
{
std::cout << "Error, maximum file size reached." << std::endl;
continue;
}
else
{
std::cout << fourth_int << " bytes written" <<std::endl;
}
delete [] third_as_char;
}
else if(!first.compare("sk") || !first.compare("lseek"))
{
 int second_int = atoi(second.c_str());
 int third_int = atoi(third.c_str());
 std::cout << third_int << std::endl;
 int check = file_system.lseek(second_int, third_int);
 if (check == -1){
    std::cout <<"File hasn't been opened" << std::endl;
    continue;
 }
 else {
      std::cout << "Current position is " << file_system.OFT[second_int].current_position << std::endl;
 }
}
}
/* Scaffolding code for testing purposes */
cin.ignore(256, '\n');
cout << "Press ENTER to continue..." << endl;
cin.get();
/* End Scaffolding */ 
    return EXIT_SUCCESS;
}

