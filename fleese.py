#!/usr/bin/python3
"""
A parametric pattern generator for Natagonia betterer sweater
"""

def svg_line(p1, p2, color="black"):
    return '<line x1="{}" y1="{}" x2="{}" y2="{}" stroke="{}"/>'.format(p1[0], p1[1], p2[0], p2[1], color)

def svg_lines_to_svg(lines):
    mx = lines_max(lines)
    mn = lines_min(lines)
    sz = (mx[0]-mn[0]+2, mx[1]-mn[1]+2)
    lines = lines_translate(lines, (-mn[0]+1, -mn[1]+1))
    lines = lines_to_int(lines)
    svg = ['<svg width="{}" height="{}">'.format(sz[0], sz[1])]
    for line in lines:
        svg.append(svg_line(line[0], line[1]))
    svg.append('</svg>')
    return "\n".join(svg)

def lines_translate(lines, t):
    for l in range(len(lines)):
        for i in range(2):
            lines[l][i] = (lines[l][i][0]+t[0], lines[l][i][1]+t[1])
    return lines

def lines_scale(lines, s):
    for l in range(len(lines)):
        for i in range(2):
            lines[l][i] = (lines[l][i][0]*s[0], lines[l][i][1]*s[1])
    return lines

def lines_max(lines):
    l = [l[0] for l in lines]
    l.extend([l[1] for l in lines])
    max_x = max([p[0] for p in l])
    max_y = max([p[1] for p in l])
    return (max_x, max_y)

def lines_min(lines):
    l = [l[0] for l in lines]
    l.extend([l[1] for l in lines])
    min_x = min([p[0] for p in l])
    min_y = min([p[1] for p in l])
    return (min_x, min_y)

def lines_to_int(lines):
    for l in range(len(lines)):
        for i in range(2):
            lines[l][i] = (round(lines[l][i][0]), round(lines[l][i][1]))
    return lines


def betterer_sweater():
    body =   [[(-29,  0),( 29,  0)], # Bottom
              [(-29,  0),(-29, 46)], # Left Side
              [( 29,  0),( 29, 46)], # Right Side
              [(-29, 46),( 29, 46)]] # Top

    top =    [[(-29, 46),( 29, 46)], # Bottom
              [(-29, 46),(-29, 68)], # Left Side
              [( 29, 46),( 29, 68)], # Right Side
              [(-12, 72),( 12, 72)], # Top
              [(-29, 68),(-12, 72)], # Left Shoulder
              [( 29, 68),( 12, 72)], # Right Shoulder
              [(  0, 72),(  0, 46)]] # Zipper

    collar = [[(-12, 72),( 12, 72)], # Bottom
              [(-12, 72),(-12, 77)], # Left Side
              [( 12, 72),( 12, 77)], # Right Side
              [(-12, 77),( 12, 77)], # Top
              [(  0, 72),(  0, 77)]] # Zipper

    arms =   [[( 29, 68),( 83, 29)], # Right Top
              [( 29, 46),( 77, 19)], # Right Bottom
              [( 77, 19),( 83, 29)], # Right Cuff
              [( 29, 68),( 29, 46)], # Right Shoulder
              [( -29, 68),( -83, 29)], # Left Top
              [( -29, 46),( -77, 19)], # Left Bottom
              [( -77, 19),( -83, 29)], # Left Cuff
              [( -29, 68),( -29, 46)]] # Left Shoulder

    lines = body + top + collar + arms
    lines = lines_scale(lines, (1, -1))
    lines = lines_translate(lines, (-lines_min(lines)[0], 0))
    return lines


def main(width=1, height=1, output="betterer_large.html"):
    lines = betterer_sweater()

    svg = svg_lines_to_svg(lines)
    text = "<h5>Natagonia Even Better Sweater</h5>\n" + svg
    with open(output, "w") as f:
        f.write(text)


if __name__ == "__main__":
    main()
