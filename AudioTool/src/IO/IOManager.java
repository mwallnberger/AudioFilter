package IO;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import Common.GeneralException;
import Common.Signal;
import IO.API.RiffChunk_RIFF;
import IO.API.RiffChunk_WAVE;
import IO.API.RiffChunk_data;
import IO.API.RiffChunk_fmt_;
import IO.API.RiffRoot;
import IO.API.Utils;

public class IOManager
{

	public static Signal importFile(File file) throws GeneralException
	{
		try
		{

			AudioFileFormat fileFormat = AudioSystem.getAudioFileFormat(file);
			float[][] values = Utils.WAVToFloats(file);
			float[] left = null;
			float[] right = null;
			if (values.length > 0)
			{
				if (values[0].length > 0)
				{
					left = new float[values.length];
				}
				if (values[0].length > 1)
				{
					right = new float[values.length];
				}

				for (int x = 0; x < values.length; x++)
				{
					if (values[x].length > 0)
					{
						left[x] = values[x][0];
					}
					if (values[x].length > 1)
					{
						right[x] = values[x][1];
					}
				}
			}
			return new Signal(fileFormat, left, right, file.getName());
		}
		catch (Exception e)
		{
			throw new GeneralException("Cannot find file!");
		}

	}

	public static void exportFile(File file, Signal signal) throws GeneralException 																
	{
		try
		{
			AudioFormat out = signal.getFormat().getFormat();

			byte[] audioRaw = convertToBytes(signal);

			InputStream b_in = new ByteArrayInputStream(audioRaw);
			
			AudioInputStream stream = new AudioInputStream(b_in, out, audioRaw.length/(signal.getFormat().getFormat().getFrameSize()));
			AudioSystem.write(stream, AudioFileFormat.Type.WAVE,  file);
		}
		catch (IOException e)
		{
			throw new GeneralException("Error!");
		}
	}

	private static float[][] convertSignalToByteArray(Signal signal)
	{
		float[][] buffer = new float[signal.getSignalLeft().length][];
		boolean isStereo = true;
		float[] sigLeft = signal.getSignalLeft();
		float[] sigRight = signal.getSignalRight();
		if (signal.getSignalRight() == null)
		{
			isStereo = false;
		}
		for (int x = 0; x < sigLeft.length; x++)
		{
			if (isStereo)
			{
				buffer[x] = new float[2];
				buffer[x][0] = sigLeft[x];
				buffer[x][1] = sigRight[x];
			}
			else
			{
				buffer[x] = new float[1];
				buffer[x][0] = sigLeft[x];
			}
		}

		return buffer;
	}

	public static byte[] convertToBytes(Signal signal)
	{
		float[][] buffer = convertSignalToByteArray(signal);
		long sampleRate = (long) signal.getFormat().getFormat().getSampleRate();
		RiffRoot root = new RiffRoot();
		RiffChunk_RIFF riffChunk = new RiffChunk_RIFF();
		RiffChunk_WAVE waveChunk = new RiffChunk_WAVE();
		RiffChunk_data dataChunk = new RiffChunk_data();
		RiffChunk_fmt_ fmtChunk = new RiffChunk_fmt_();
		fmtChunk.setAudioChannelCount(buffer[0].length);
		fmtChunk.setAudioFormatCode(1);// 1 for PCM
		fmtChunk.setBitsPerSample(signal.getFormat().getFormat().getSampleSizeInBits());
		fmtChunk.setBlockAlign(2 * buffer[0].length);
		fmtChunk.setByteRate(sampleRate * 2 * fmtChunk.getAudioChannelCount());
		fmtChunk.setSampleRate(sampleRate);
		fmtChunk.setSize(16);// 16 for PCM
		waveChunk.addChildChunk(dataChunk);
		waveChunk.addChildChunk(fmtChunk);
		riffChunk.addChildChunk(waveChunk);
		waveChunk.setAudioFromFloats(buffer);
		root.addChildChunk(riffChunk);
		ByteBuffer fileBuffer = ByteBuffer.allocateDirect(dataChunk._sizeEstimateInBytes());
		fileBuffer.order(ByteOrder.LITTLE_ENDIAN);
		fileBuffer.rewind();
		try
		{
			dataChunk._toData(fileBuffer);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		fileBuffer.rewind();
		fileBuffer.getFloat();
		byte[] b = new byte[fileBuffer.remaining()];
		fileBuffer.get(b);
		return dataChunk.getRawData();
	}
}
