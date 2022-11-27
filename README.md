This program was created in order to transfer any of your images to Minecraft.
The maximum size in height is 256 blocks (320 since version 1.18).
The program accepts an image for processing as input.
At the output, you will receive a .mcfunction file (or a datapack with this file).
All that will have to be done next is to get to the right place in the game
and run this function with the /function command (the lower left corner of the
image/building will be located in the place from where the function will start).

You can create a regular image, a skin (with one layer) or a block (with one texture)

Creating images:
java -jar &lt;program path&gt; [options] &lt;images&gt; [-o <output files>]

You can specify multiple images and multiple output files,
each pair will be processed separately.

You can also set the color space through which the image will be converted
(RGB, XYZ, YCbCr, Lab are supported). Usually, the higher the quality, the slower.
IMHO, the closest color is chosen by the Lab. RGB is the fastest, but the image
may turn out to be very distorted in color.

P.S. It requires the ArgParser library to work
