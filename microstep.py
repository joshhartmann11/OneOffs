"""
This script is to provide analog values for the microsteps of stepper motors
and formats it c-like
"""
import math

DIVISOR = 64

def rotate(l, n):
    return l[n:] + l[:n]

sample_time = [i * (1/DIVISOR) for i in range(DIVISOR)]
sin_values = [(math.sin(2 * math.pi * s) * 127.5 + 127.5) for s in sample_time]

s1 = sin_values
s2 = rotate(sin_values, int(1*DIVISOR/4))
s3 = rotate(sin_values, int(2*DIVISOR/4))
s4 = rotate(sin_values, int(3*DIVISOR/4))

print("{")
for i in range(DIVISOR):
    print("  {%3d, %3d, %3d, %3d}" % (s1[i], s2[i], s3[i], s4[i]), end="")
    if i == DIVISOR-1:
        print()
    else:
        print(",")
print("}")
