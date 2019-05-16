/*
 ============================================================================
 Name        : test.c
 Author      : 
 Version     :
 Copyright   : Your copyright notice
 Description : Hello World in C, Ansi-style
 ============================================================================
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>
#include <stdbool.h>

#define SEQUENCE_LENGTH (50)
#define OUTPUT_FILE ("output.txt")
#define INPUT_FILE ("note_sequence.txt")

typedef struct{
	char freq[2];
	float length;
	int volume;
}note;

void print_note(int freq, float length, int bpm, FILE* out, int volume){//trying to add volume

	float mul = ((float)freq) / ((float)390);//speed of speaker movement dependent on frequency
	int x;
	puts("hello");
	volume *= 5;
	//%40 - div 5.41
	//%20 - div 10.8
	//%10 - div 21.6
	//%5  - div 43.2
	//div = 216/%

	for(x = 0; x < (2940*bpm*length); x++){
		fprintf(out,"%c", (int)((((float)x) * mul) / (float)(216.0 / (float)volume)) % 20);
	}//for
	fprintf(out,"\n");
}//print_note

void print_note_wave(int freq, float length, int bpm, FILE* out, int volume){
	float mul = ((float)freq) / ((float)190);//speed of speaker movement dependent on frequency
	int time = 0;
	bool down = false;// true if the sequence is going down
	int hexPrint = 0;// the character printed to the output
	while(time < (2940*bpm*length)){

		fprintf(out,"%c", hexPrint);
		if(hexPrint == volume * 5) down = true;
		if(hexPrint == 0) down = false;
		if(down == true) hexPrint--;
		if(down == false) hexPrint++;
	}//while


}//print_note_wave

void note_creator(note* current, int bpm, FILE* out, int volume){
	printf("note = %s  length = %f  volume = %d\n", current->freq, current->length, volume);
	int sharp;
	current->volume = volume;
	if(current->freq[1] == '#') sharp = 19;//if the note is sharp, increase frequency (freq)

	switch(current->freq[0]){
	case('a'): print_note((440 + sharp),current->length, bpm, out, current->volume);
		break;
	case('b'): print_note((494 + sharp),current->length, bpm, out, current->volume);
		break;
	case('c'): print_note((262 + sharp),current->length, bpm, out, current->volume);
		break;
	case('d'): print_note((294 + sharp),current->length, bpm, out, current->volume);
		break;
	case('e'): print_note((330 + sharp),current->length, bpm, out, current->volume);
		break;
	case('f'): print_note((349 + sharp),current->length, bpm, out, current->volume);
		break;
	case('g'): print_note((392 + sharp),current->length, bpm, out, current->volume);
		break;
	}//switch case

}//note_creator

void instructions(){
	puts("INSTRUCTIONS:\n");
	puts("- Enter 1 note per line followed by the number of beats the note has");
	puts("    - A note can be from c -> b and follow note by a # symbol to make the note sharp");
	puts("- To change the volume use the \"v\" character followed by the volume from 1 to 10\n");
}//instructions


int main(void) {
	FILE* in = fopen(INPUT_FILE, "r");
	FILE* out = fopen(OUTPUT_FILE, "w");
	int bpm;//timing
	int x; //counting variable
	char call[10];

	puts("Enter \"help\" for instructions");
	puts("Enter \"start\" to start");
	printf("The max sequence length is: %d notes\n", SEQUENCE_LENGTH);

	scanf("%s", call);
	if(strcmp(call, "help") == 0) instructions();
	puts("Enter how many beats per minute (bpm)?");
	scanf("%d", &bpm);
	note current;
	int volume = 5;//Volume from 1 to 10
	x = 0;

	while(x < SEQUENCE_LENGTH){
		if(fscanf(in, "%s", current.freq) == EOF) break;
		else if(fscanf(in, "%f", &current.length) == EOF) break;
		else if(current.freq[0] == 'v') volume = ((int)(current.length) % 11);
		else note_creator(&current, bpm, out, volume);
		x++;
	}//while(1)

	fclose(in);
	fclose(out);
	return EXIT_SUCCESS;
}//main
