package com.scm.validators;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FileValidator implements ConstraintValidator<ValidFile, MultipartFile>{

    private static final long MAX_FILE_SIZE=1024*1024*3;
    //5 mb
    //type
    
    //height nad width
	@Override
	public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
		if(file==null || file.isEmpty()) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate("File cannot be empty").addConstraintViolation();
			return false;
		}
		
		
		System.out.println("Size of file is : "+file.getSize());
		if(file.getSize()>MAX_FILE_SIZE) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate("File size must be less than 3 Mb").addConstraintViolation();
			return false;
		}
		
		// for resolution
		
//		try {
//			BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
//			
//			if(bufferedImage.getHeight()>000 && bufferedImage.getWidth()>00) {
//				return false;
//			}
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} 
		return true;
			
	}

}
