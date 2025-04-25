package Games;

public class Cube {
    public static float r = 7.0f;

    public static float A, B, C;

    public static int height = (int)(r * 5) + 1;
    public static int width = (int)Math.round(r*12.5f) + 1;

    public static int x = width/2;
    public static int y = height/2;

    public static char [][] buffer;
    public static char [] faces = {'@', '#', '$', '%', '&', '*'};

    public float [][] cubeVerticies = new float [][] {
            {-r, -r, -r}, {-r, -r, r}, {-r, r, -r}, {-r, r, r},
            {r, -r, -r}, {r, -r, r}, {r, r, -r}, {r, r, r}
        };

    int[][] cubeEdges = {
            {0, 1}, {1, 3}, {3, 2}, {2, 0},
            {4, 5}, {5, 7}, {7, 6}, {6, 4},
            {0, 4}, {1, 5}, {2, 6}, {3, 7}
        };


    public Cube() { }

    public void run() {
        //avoids CWE-835: Loop with Unreachable Exit Condition (‘Infinite Loop’) her by using a for loop
        for (int i = 0; i < 5000; i++) {
            buffer = new char [width][height];
            A += 0.005f;
            B += 0.005f;
            C += 0.001f;
            for (int j = 0; j < 12; j++) {
                drawVector(
                    (int)Math.round(rotateX(cubeVerticies[cubeEdges[j][0]])*2) + x,
                    (int)Math.round(rotateY(cubeVerticies[cubeEdges[j][0]])) + y,
                    (int)Math.round(rotateX(cubeVerticies[cubeEdges[j][1]])*2) + x,
                    (int)Math.round(rotateY(cubeVerticies[cubeEdges[j][1]])) + y
                );
            }
            System.out.print(toDisplay(buffer));
        }
    }

    public float [] rotate(float [] coord){
        float [] newCoord = {rotateX(coord), rotateY(coord), rotateZ(coord)};
        return newCoord;
    }

    public static float rotateX(float [] v){ //avoids CWE-783: Operator Precedence Logic Error for proper rotation equation
        return (float)(v[1] * Math.sin(A) * Math.sin(B) * Math.cos(C) - v[2] * Math.cos(A) * Math.sin(B) * Math.cos(C) +
         v[1] * Math.cos(A) * Math.sin(C) + v[2] * Math.sin(A) * Math.sin(C) + v[0] * Math.cos(B) * Math.cos(C));
    }

    public static float rotateY(float [] v) { //avoids CWE-783: Operator Precedence Logic Error for proper rotation equation
        return (float)(v[1] * Math.cos(A) * Math.cos(C) + v[2] * Math.sin(A) * Math.cos(C) -
               v[1] * Math.sin(A) * Math.sin(B) * Math.sin(C) + v[2] * Math.cos(A) * Math.sin(B) * Math.sin(C) -
               v[0] * Math.cos(B) * Math.sin(C));
    }

    public static float rotateZ(float [] v) { //avoids CWE-783: Operator Precedence Logic Error for proper rotation equation
        return (float)(v[2] * Math.cos(A) * Math.cos(B) - v[1] * Math.sin(A) * Math.cos(B) + v[0] * Math.sin(B));
    }

    public static void fill(int [] coord1, int [] coord2, int [] coord3, int [] coord4){

    }

    public static void drawVector(int x0, int y0, int x1, int y1){
        if (Math.abs(y1 - y0) < Math.abs(x1 - x0))
            if (x0 > x1)
                plotLineLow(x1, y1, x0, y0);
            else
                plotLineLow(x0, y0, x1, y1);
        else
            if (y0 > y1)
                plotLineHigh(x1, y1, x0, y0);
            else
                plotLineHigh(x0, y0, x1, y1);
    }

    public static void plotLineLow(int x0, int y0, int x1, int y1){
        int dx = x1 - x0;
        int dy = y1 - y0;
        int yi = 1;
        if (dy < 0){
            yi = -1;
            dy = -dy;
        }
        int D = (2 * dy) - dx;
        int y = y0;
        for (int x = x0; x < x1; x++){
            buffer[x][y] = '-';
            if (D > 0) {
                y = y + yi;
                D = D + (2 * (dy - dx));
            }
            else
                D = D + 2*dy;
        }
    }

    public static void plotLineHigh(int x0, int y0, int x1, int y1){
        int dx = x1 - x0;
        int dy = y1 - y0;
        int xi = 1;
        if (dx < 0) {
            xi = -1;
            dx = -dx;
        }
        int D = (2 * dx) - dy;
        int x = x0;

        for (int y = y0; y < y1; y++){
            buffer[x][y] = '|';
            if (D > 0) {
                x = x + xi;
                D = D + (2 * (dx - dy));
            }  
            else
                D = D + 2*dx;
        }
    }

    public static String  toDisplay(char [][] display){
        StringBuilder builder = new StringBuilder();
        builder.append("\033[2J");
        builder.append("\033[H");
        for(int i = height-1; i > -1; i--){
            for (int j = 0; j < width; j++){
                if (display[j][i] != '\u0000')
                    builder.append(display[j][i]);
                else
                    builder.append(" ");
            }
            builder.append("\n");
        }
        return builder.toString();
    }
}
