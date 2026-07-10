package com.uce.programacion2.Dungeon_Crawler.GUI;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * Utilidad centralizada para carga, escalado y generación de imágenes.
 *
 * PARA REEMPLAZAR PLACEHOLDERS: coloca tus imágenes PNG en:
 *   src/main/resources/monsters/  → pyron.png, aquos.png, floran.png, voltix.png, umbrix.png
 *   src/main/resources/trainer/   → trainer.png
 *   src/main/resources/regions/   → bosque_verde.png, lago_cristal.png, cueva_oscura.png,
 *                                    volcan_rojo.png, ruinas_antiguas.png, pantano_toxico.png
 *   src/main/resources/backgrounds/ → batalla.png, menu.png
 *   src/main/resources/icons/     → pocion.png, esfera.png
 *
 * Todas las imágenes se cargan via getResourceAsStream() — funciona dentro del JAR.
 */
public final class ImageUtils {

    private static final Map<String, BufferedImage> CACHE = new HashMap<>();

    // Colores temáticos por criatura — usados en placeholders y badges
    public static final Color COLOR_PYRON   = new Color(255, 100, 20);
    public static final Color COLOR_AQUOS   = new Color(30,  140, 255);
    public static final Color COLOR_FLORAN  = new Color(50,  190, 70);
    public static final Color COLOR_VOLTIX  = new Color(230, 210, 20);
    public static final Color COLOR_UMBRIX  = new Color(140, 30,  200);
    public static final Color COLOR_TRAINER = new Color(80,  140, 220);

    private ImageUtils() {}

    /** Color temático asociado al nombre de una criatura. */
    public static Color colorDeCriatura(String nombre) {
        return switch (nombre) {
            case "Pyron"  -> COLOR_PYRON;
            case "Aquos"  -> COLOR_AQUOS;
            case "Floran" -> COLOR_FLORAN;
            case "Voltix" -> COLOR_VOLTIX;
            case "Umbrix" -> COLOR_UMBRIX;
            default       -> new Color(160, 160, 160);
        };
    }

    /** Color temático asociado al tipo de una criatura. */
    public static Color colorDeTipo(String tipo) {
        return switch (tipo) {
            case "Fuego"     -> COLOR_PYRON;
            case "Agua"      -> COLOR_AQUOS;
            case "Planta"    -> COLOR_FLORAN;
            case "Eléctrico" -> COLOR_VOLTIX;
            case "Oscuro"    -> COLOR_UMBRIX;
            default          -> new Color(120, 120, 160);
        };
    }

    // ======================================================================
    // CARGA DESDE RECURSOS
    // ======================================================================

    /** Intenta cargar una imagen desde el classpath; devuelve null si no existe. */
    private static BufferedImage cargarRecurso(String ruta) {
        if (CACHE.containsKey(ruta)) return CACHE.get(ruta);
        try (InputStream is = ImageUtils.class.getResourceAsStream(ruta)) {
            if (is != null) {
                BufferedImage img = ImageIO.read(is);
                CACHE.put(ruta, img);
                return img;
            }
        } catch (IOException ignored) {}
        return null;
    }

    /** Escala una imagen al tamaño indicado con interpolación bicúbica. */
    public static BufferedImage escalar(BufferedImage src, int w, int h) {
        if (src == null) return null;
        BufferedImage out = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = out.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,  RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,   RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_RENDERING,      RenderingHints.VALUE_RENDER_QUALITY);
        g.drawImage(src, 0, 0, w, h, null);
        g.dispose();
        return out;
    }

    // ======================================================================
    // ICONOS DE MONSTRUO
    // ======================================================================

    /**
     * Devuelve un ImageIcon del monstruo escalado a w×h.
     * Usa /monsters/nombre_lower.png si existe; si no, genera placeholder.
     */
    public static ImageIcon iconoMonstruo(String nombre, int w, int h) {
        String ruta = "/monsters/" + nombre.toLowerCase() + ".png";
        BufferedImage img = cargarRecurso(ruta);
        if (img != null) return new ImageIcon(escalar(img, w, h));
        return new ImageIcon(placeholderMonstruo(nombre, colorDeCriatura(nombre), w, h));
    }

    // ======================================================================
    // ICONO DEL ENTRENADOR
    // ======================================================================

    public static ImageIcon iconoEntrenador(int w, int h) {
        BufferedImage img = cargarRecurso("/trainer/trainer.png");
        if (img != null) return new ImageIcon(escalar(img, w, h));
        return new ImageIcon(placeholderEntrenador(w, h));
    }

    // ======================================================================
    // ICONO DE REGIÓN
    // ======================================================================

    public static ImageIcon iconoRegion(String nombre, int w, int h) {
        String clave = normalizar(nombre);
        BufferedImage img = cargarRecurso("/regions/" + clave + ".png");
        if (img != null) return new ImageIcon(escalar(img, w, h));
        return new ImageIcon(placeholderRegion(nombre, colorRegion(nombre), w, h));
    }

    private static String normalizar(String s) {
        return s.toLowerCase()
                .replace(' ', '_')
                .replace('á', 'a').replace('é', 'e').replace('í', 'i')
                .replace('ó', 'o').replace('ú', 'u')
                .replace('ñ', 'n');
    }

    private static Color colorRegion(String nombre) {
        String n = nombre.toLowerCase();
        if (n.contains("bosque"))  return new Color(20,  80,  30);
        if (n.contains("lago"))    return new Color(15,  70, 160);
        if (n.contains("cueva"))   return new Color(30,  20,  55);
        if (n.contains("volc"))    return new Color(160, 40,   5);
        if (n.contains("ruinas"))  return new Color(90,  70,  40);
        if (n.contains("pantano")) return new Color(40,  70,  35);
        return new Color(50, 50, 90);
    }

    // ======================================================================
    // GENERADORES DE PLACEHOLDER
    // ======================================================================

    /** Arte programático para un monstruo (imagen circular con glow y símbolo de tipo). */
    public static BufferedImage placeholderMonstruo(String nombre, Color color, int w, int h) {
        String cacheKey = "m_" + nombre + "_" + w + "_" + h;
        if (CACHE.containsKey(cacheKey)) return CACHE.get(cacheKey);

        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        aplicarHints(g);

        int cx = w / 2;
        int cy = (int) (h * 0.44);
        int radio = (int) (Math.min(w, h) * 0.34);

        // Glow exterior multicapa
        for (int i = 6; i >= 1; i--) {
            int alpha = 18 * i;
            g.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), Math.min(alpha, 255)));
            int pad = i * 7;
            g.fillOval(cx - radio - pad, cy - radio - pad, (radio + pad) * 2, (radio + pad) * 2);
        }

        // Cuerpo principal
        GradientPaint body = new GradientPaint(
                cx - radio, cy - radio, color.brighter(),
                cx + radio, cy + radio, color.darker().darker());
        g.setPaint(body);
        g.fillOval(cx - radio, cy - radio, radio * 2, radio * 2);

        // Brillo superior (especular)
        g.setColor(new Color(255, 255, 255, 55));
        g.fillOval(cx - radio / 2, cy - radio + radio / 8, radio, radio / 2);

        // Borde brillante
        g.setStroke(new BasicStroke(2.5f));
        g.setColor(color.brighter().brighter());
        g.drawOval(cx - radio, cy - radio, radio * 2, radio * 2);

        // Ojos
        dibujarOjos(g, cx, cy, radio);

        // Símbolo del tipo
        dibujarSimboloTipo(g, nombre, color, cx, cy + radio + 14, radio / 2);

        // Sombra base
        g.setColor(new Color(0, 0, 0, 55));
        g.fillOval(cx - radio + 12, h - 20, (radio - 12) * 2, 12);

        // Nombre
        Font fnt = new Font("Serif", Font.BOLD, Math.max(10, w / 10));
        g.setFont(fnt);
        FontMetrics fm = g.getFontMetrics();
        int tx = cx - fm.stringWidth(nombre) / 2;
        g.setColor(new Color(0, 0, 0, 140));
        g.drawString(nombre, tx + 1, h - 5 + 1);
        g.setColor(color.brighter().brighter());
        g.drawString(nombre, tx, h - 5);

        g.dispose();
        CACHE.put(cacheKey, img);
        return img;
    }

    private static void dibujarOjos(Graphics2D g, int cx, int cy, int radio) {
        int eyeY = cy - radio / 5;
        int er   = Math.max(4, radio / 6);
        int pr   = Math.max(2, er / 2);
        g.setColor(Color.WHITE);
        g.fillOval(cx - radio / 3 - er, eyeY - er, er * 2, er * 2);
        g.fillOval(cx + radio / 3 - er, eyeY - er, er * 2, er * 2);
        g.setColor(new Color(15, 15, 15));
        g.fillOval(cx - radio / 3 - pr, eyeY - pr, pr * 2, pr * 2);
        g.fillOval(cx + radio / 3 - pr, eyeY - pr, pr * 2, pr * 2);
        // Destello blanco
        g.setColor(new Color(255, 255, 255, 180));
        g.fillOval(cx - radio / 3 - pr + 1, eyeY - pr + 1, pr, pr);
        g.fillOval(cx + radio / 3 - pr + 1, eyeY - pr + 1, pr, pr);
    }

    private static void dibujarSimboloTipo(Graphics2D g, String nombre, Color color,
                                            int cx, int baseY, int s) {
        g.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 210));
        switch (nombre) {
            case "Pyron" -> {
                int[] fx = { cx, cx - s, cx + s };
                int[] fy = { baseY - s, baseY + s / 2, baseY + s / 2 };
                g.fillPolygon(fx, fy, 3);
                // llama interior
                g.setColor(new Color(255, 240, 80, 180));
                int[] fx2 = { cx, cx - s / 2, cx + s / 2 };
                int[] fy2 = { baseY - s / 2, baseY + s / 3, baseY + s / 3 };
                g.fillPolygon(fx2, fy2, 3);
            }
            case "Aquos" -> {
                int[] dx = { cx, cx - s, cx + s };
                int[] dy = { baseY - s, baseY + s / 3, baseY + s / 3 };
                g.fillPolygon(dx, dy, 3);
                g.fillOval(cx - s, baseY + s / 3 - s / 2, s * 2, s);
            }
            case "Floran" -> {
                g.fillOval(cx - s / 2, baseY - s, s, (int)(s * 1.6));
                g.fillOval(cx - s, baseY - s / 2, (int)(s * 1.6), s);
                g.setColor(new Color(30, 120, 40, 200));
                g.setStroke(new BasicStroke(2f));
                g.drawLine(cx, baseY - s, cx, baseY + s / 2);
            }
            case "Voltix" -> {
                int[] lx = { cx - s / 4, cx + s / 2, cx, cx + s / 4, cx - s / 2, cx };
                int[] ly = { baseY - s, baseY - s / 5, baseY - s / 5, baseY + s, baseY + s / 5, baseY + s / 5 };
                g.fillPolygon(lx, ly, 6);
            }
            case "Umbrix" -> {
                g.fillOval(cx - s, baseY - s, s * 2, s * 2);
                g.setColor(new Color(10, 5, 30));
                g.fillOval(cx - s / 3, baseY - s, (int)(s * 1.7), s * 2);
            }
            default -> g.fillOval(cx - s / 2, baseY - s / 2, s, s);
        }
    }

    /** Silueta estilizada de entrenador. */
    public static BufferedImage placeholderEntrenador(int w, int h) {
        String cacheKey = "trainer_" + w + "_" + h;
        if (CACHE.containsKey(cacheKey)) return CACHE.get(cacheKey);

        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        aplicarHints(g);

        Color base  = COLOR_TRAINER;
        Color skin  = new Color(255, 205, 160);
        int cx      = w / 2;
        int headR   = Math.max(18, w / 5);
        int headY   = (int) (h * 0.10);
        int bodyW   = w / 3;
        int bodyH   = (int) (h * 0.38);
        int bodyY   = headY + headR * 2 - 4;
        int legH    = (int) (h * 0.25);
        int legW    = bodyW / 3;

        // Sombra base
        g.setColor(new Color(0, 0, 0, 60));
        g.fillOval(cx - w / 4, h - 18, w / 2, 12);

        // Piernas
        g.setColor(new Color(35, 50, 110));
        g.fillRoundRect(cx - bodyW / 2 + 3, bodyY + bodyH - 8, legW, legH, 6, 6);
        g.fillRoundRect(cx + bodyW / 2 - legW - 3, bodyY + bodyH - 8, legW, legH, 6, 6);
        // Zapatos
        g.setColor(new Color(20, 20, 20));
        g.fillRoundRect(cx - bodyW / 2 + 2, bodyY + bodyH + legH - 12, legW + 4, 12, 4, 4);
        g.fillRoundRect(cx + bodyW / 2 - legW - 4, bodyY + bodyH + legH - 12, legW + 4, 12, 4, 4);

        // Cuerpo
        GradientPaint bodyGrad = new GradientPaint(cx - bodyW / 2, bodyY, base.brighter(), cx + bodyW / 2, bodyY + bodyH, base.darker());
        g.setPaint(bodyGrad);
        g.fillRoundRect(cx - bodyW / 2, bodyY, bodyW, bodyH, 10, 10);

        // Brazos
        g.setColor(base);
        g.fillRoundRect(cx - bodyW / 2 - legW + 2, bodyY + 6, legW, bodyH / 2, 6, 6);
        g.fillRoundRect(cx + bodyW / 2 + 2, bodyY + 6, legW, bodyH / 2, 6, 6);

        // Esfera en la mano izquierda
        Color esferaColor = new Color(220, 80, 30);
        g.setColor(esferaColor);
        int esfX = cx - bodyW / 2 - legW - 4;
        int esfY = bodyY + bodyH / 3;
        g.fillOval(esfX, esfY, 16, 16);
        g.setColor(new Color(255, 120, 60));
        g.fillOval(esfX + 4, esfY + 2, 6, 5);

        // Cabeza
        GradientPaint headGrad = new GradientPaint(cx, headY, skin.brighter(), cx, headY + headR * 2, skin);
        g.setPaint(headGrad);
        g.fillOval(cx - headR, headY, headR * 2, headR * 2);

        // Gorra
        g.setColor(base.darker());
        g.fillRoundRect(cx - headR - 5, headY + headR / 2, headR * 2 + 10, headR / 2 + 2, 4, 4);
        g.fillOval(cx - headR + 4, headY - headR / 3, headR * 2 - 8, headR);
        // Visera
        g.setColor(base.darker().darker());
        g.fillRoundRect(cx - headR - 8, headY + headR / 2, headR * 2 + 16, 6, 3, 3);

        // Ojos
        int eyeY = headY + (int)(headR * 1.1);
        g.setColor(new Color(30, 20, 10));
        g.fillOval(cx - headR / 3 - 3, eyeY - 3, 7, 6);
        g.fillOval(cx + headR / 3 - 3, eyeY - 3, 7, 6);
        g.setColor(Color.WHITE);
        g.fillOval(cx - headR / 3, eyeY - 2, 3, 3);
        g.fillOval(cx + headR / 3, eyeY - 2, 3, 3);

        // Sonrisa
        g.setColor(new Color(180, 100, 70));
        g.setStroke(new BasicStroke(1.5f));
        g.drawArc(cx - headR / 4, eyeY + 4, headR / 2, headR / 4, 200, 140);

        // Glow
        for (int i = 3; i >= 1; i--) {
            g.setColor(new Color(80, 140, 220, 18 * i));
            g.setStroke(new BasicStroke(i * 5f));
            g.drawOval(cx - w / 3, headY - 8, w * 2 / 3, h - headY);
        }

        g.dispose();
        CACHE.put(cacheKey, img);
        return img;
    }

    /** Paisaje generado programáticamente para una región del mundo. */
    public static BufferedImage placeholderRegion(String nombre, Color colorBase, int w, int h) {
        String cacheKey = "reg_" + nombre + "_" + w + "_" + h;
        if (CACHE.containsKey(cacheKey)) return CACHE.get(cacheKey);

        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        aplicarHints(g);

        int groundY = (int) (h * 0.58);

        // Cielo degradado
        g.setPaint(new GradientPaint(0, 0, colorBase.darker().darker(), 0, groundY, colorBase));
        g.fillRect(0, 0, w, h);

        // Suelo
        g.setPaint(new GradientPaint(0, groundY, colorBase.darker(), 0, h, colorBase.darker().darker()));
        g.fillRect(0, groundY, w, h - groundY);

        // Elementos temáticos
        dibujarElementosRegion(g, nombre, colorBase, w, h, groundY);

        // Viñeta oscura (bordes)
        RadialGradientPaint vignette = new RadialGradientPaint(
                new Point(w / 2, h / 2),
                Math.max(w, h) * 0.7f,
                new float[]{ 0.4f, 1.0f },
                new Color[]{ new Color(0,0,0,0), new Color(0,0,0,140) });
        g.setPaint(vignette);
        g.fillRect(0, 0, w, h);

        // Nombre en la parte inferior con sombra
        g.setFont(new Font("Serif", Font.BOLD, Math.max(13, w / 14)));
        FontMetrics fm = g.getFontMetrics();
        int tx = (w - fm.stringWidth(nombre)) / 2;
        g.setColor(new Color(0, 0, 0, 160));
        g.drawString(nombre, tx + 2, h - 12 + 2);
        g.setColor(new Color(255, 220, 70));
        g.drawString(nombre, tx, h - 12);

        g.dispose();
        CACHE.put(cacheKey, img);
        return img;
    }

    private static void dibujarElementosRegion(Graphics2D g, String nombre, Color colorBase,
                                                int w, int h, int groundY) {
        String n = nombre.toLowerCase();

        if (n.contains("bosque")) {
            g.setColor(new Color(15, 70, 20));
            for (int i = 0; i < 6; i++) {
                int tx = w / 7 + i * w / 6 - w / 12;
                int tH = h / 3 + (i % 3) * h / 9;
                g.fillOval(tx, groundY - tH, w / 8, tH);
                g.setColor(new Color(50, 35, 15));
                g.fillRect(tx + w / 18, groundY - 14, 9, 18);
                g.setColor(new Color(15, 70, 20));
            }
        } else if (n.contains("lago") || n.contains("cristal")) {
            g.setColor(new Color(20, 90, 200, 190));
            g.fillOval(w / 8, groundY - 14, (int)(w * 0.75), h / 4 + 10);
            g.setColor(new Color(100, 190, 255, 80));
            g.fillOval(w / 4, groundY + 10, (int)(w * 0.5), h / 8);
            // Montañas al fondo
            g.setColor(new Color(colorBase.getRed() / 2, colorBase.getGreen() / 2, colorBase.getBlue() / 2, 180));
            int[] mx = {0, w / 4, w / 2, 3 * w / 4, w};
            int[] my = {groundY, groundY - h / 4, groundY - h / 3, groundY - h / 5, groundY};
            g.fillPolygon(mx, my, 5);
        } else if (n.contains("cueva")) {
            g.setColor(new Color(15, 10, 35));
            int[] px = {w / 4, w / 2, 3 * w / 4};
            int[] py = {groundY, groundY - h / 2, groundY};
            g.fillPolygon(px, py, 3);
            g.setColor(new Color(5, 3, 18));
            g.fillOval(w / 2 - w / 9, groundY - h / 3, w / 4, h / 3);
            // Estalagmitas
            g.setColor(new Color(40, 30, 60));
            for (int i = 0; i < 5; i++) {
                int stx = w / 8 + i * w / 6;
                int stH = h / 8 + (i % 2) * h / 12;
                int[] sx = {stx - 8, stx + 8, stx};
                int[] sy = {groundY + stH / 4, groundY + stH / 4, groundY - stH};
                g.fillPolygon(sx, sy, 3);
            }
        } else if (n.contains("volc")) {
            g.setColor(new Color(80, 30, 8));
            int[] vx = {w / 5, w / 2, 4 * w / 5};
            int[] vy = {groundY, groundY - (int)(h * 0.55), groundY};
            g.fillPolygon(vx, vy, 3);
            // Lava
            g.setColor(new Color(255, 80, 10, 210));
            g.fillOval(w / 2 - 22, groundY - (int)(h * 0.55) - 10, 44, 44);
            for (int i = 0; i < 4; i++) {
                g.setColor(new Color(255, 60 + i * 20, 10, 160 - i * 30));
                g.fillOval(w / 2 - 14 + (i % 2) * 8 - 4, groundY - (int)(h * 0.55) + i * 20, 28, 28);
            }
            // Grietas de lava en el suelo
            g.setColor(new Color(220, 60, 5, 140));
            g.setStroke(new BasicStroke(3f));
            g.drawLine(w / 4, groundY + 20, w * 3 / 4, groundY + 30);
            g.drawLine(w / 3, groundY + 40, w * 2 / 3, groundY + 50);
        } else if (n.contains("ruinas")) {
            g.setColor(new Color(120, 100, 65));
            for (int i = 0; i < 4; i++) {
                int px = w / 5 + i * w / 5;
                int colH = h / 3 + (i % 2) * h / 8;
                g.fillRect(px - 9, groundY - colH, 18, colH);
                g.fillRect(px - 14, groundY - colH - 12, 28, 14);
                // Agrietado
                g.setColor(new Color(80, 60, 35));
                g.setStroke(new BasicStroke(1.5f));
                g.drawLine(px - 5, groundY - colH + 10, px + 2, groundY - colH + colH / 3);
                g.setColor(new Color(120, 100, 65));
            }
        } else if (n.contains("pantano")) {
            g.setColor(new Color(35, 70, 30, 200));
            g.fillOval(w / 10, groundY - 8, (int)(w * 0.8), h / 4);
            // Plantas
            g.setColor(new Color(40, 100, 35));
            for (int i = 0; i < 5; i++) {
                int px = w / 8 + i * w / 5;
                g.fillOval(px, groundY - 35, 20, 50);
                g.fillOval(px - 10, groundY - 20, 15, 40);
            }
            // Burbujas tóxicas
            g.setColor(new Color(80, 160, 40, 120));
            int[] bx = {w/4, w/2, 3*w/4, w/3};
            int[] by = {groundY + 15, groundY + 8, groundY + 12, groundY + 20};
            int[] br = {8, 12, 6, 10};
            for (int i = 0; i < bx.length; i++) g.fillOval(bx[i]-br[i], by[i]-br[i], br[i]*2, br[i]*2);
        }
    }

    // ======================================================================
    // UTILIDADES COMPARTIDAS
    // ======================================================================

    private static void aplicarHints(Graphics2D g) {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,       RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,  RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_RENDERING,          RenderingHints.VALUE_RENDER_QUALITY);
    }

}
